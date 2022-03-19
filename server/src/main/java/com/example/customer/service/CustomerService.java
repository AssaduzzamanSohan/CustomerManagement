package com.example.customer.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.customer.constants.ActionType;
import com.example.customer.message.MessageBuilder;
import com.example.customer.message.MessageHeader;
import com.example.customer.message.interfaces.Message;
import com.example.customer.model.Customer;
import com.example.customer.model.CustomerAudit;
import com.example.customer.repository.CustomerAuditRepository;
import com.example.customer.repository.CustomerRepository;

public class CustomerService extends AbstractService<Customer> {
	private static Logger log = LogManager.getLogger(CustomerService.class);

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	CustomerAuditRepository customerAuditRepo;

	@Value("${create.demo.customer}")
	private boolean createDemoCustomer;

	@PostConstruct
	public void init() {
		if (!createDemoCustomer)
			return;

		Customer admin = getAdminCustomer();
		for (int i = 2; i <= 20; i++) {
			try {
				admin.setLoginName("admin" + i);
				save(admin);
			} catch (Exception e) {
				log.error("Exception inserting Customer", e);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Message<?> serviceSingle(Message msg) throws Exception {

		MessageHeader msgHeader = null;
		Message<?> msgResponse = null;

		try {

			msgHeader = msg.getHeader();

			String action = msgHeader.getActionType();
			log.debug("Processing ACTION [{}]", action);

			List<Customer> customerList = isEmptyRequest(msg);

			if (action.equals(ActionType.NEW.toString())) {
				msgResponse = MessageBuilder.withPayload(save(customerList.get(0))).copyHeadersIfAbsent(msgHeader)
						.build();
			} else if (action.equals(ActionType.SELECT.toString())) {
				msgResponse = MessageBuilder.withPayload(select(customerList.get(0))).copyHeadersIfAbsent(msgHeader)
						.build();
			} else if (action.equals(ActionType.SELECT_ALL.toString())) {
				msgResponse = MessageBuilder.withPayload(selectAll()).copyHeadersIfAbsent(msgHeader).build();
			} else if (action.equals(ActionType.SELECT_ALL_AUDIT.toString())) {
				msgResponse = MessageBuilder.withPayload(selectAllAudit()).copyHeadersIfAbsent(msgHeader).build();
			} else if (action.equals(ActionType.UPDATE.toString())) {
				msgResponse = MessageBuilder.withPayload(update(customerList.get(0))).copyHeadersIfAbsent(msgHeader)
						.build();
			} else if (action.equals(ActionType.DELETE.toString())) {
				msgResponse = MessageBuilder.withPayload(delete(customerList.get(0))).copyHeadersIfAbsent(msgHeader)
						.build();
			} else if (action.equals(ActionType.LOGIN.toString())) {
				msgResponse = MessageBuilder.withPayload(selectByLoginNameAndPassword(customerList.get(0)))
						.copyHeadersIfAbsent(msgHeader).build();
			} else if (action.equals(ActionType.SEARCH.toString())) {
				msgResponse = MessageBuilder.withPayload(search(customerList.get(0))).copyHeadersIfAbsent(msgHeader)
						.build();
			} else if (action.equals(ActionType.SEARCH_AUDIT.toString())) {
				msgResponse = MessageBuilder.withPayload(searchAudit(customerList.get(0)))
						.copyHeadersIfAbsent(msgHeader).build();
			} else {
				throw new Exception("Unknown action " + action);
			}
		} catch (Exception ex) {
			log.error("Error {}", ex);
			throw ex;
		}

		return msgResponse;
	}

	private List<Customer> search(Customer customer) throws Exception {
		try {
			return customerRepo.findByLoginName(customer.getLoginName());
		} catch (Exception e) {
			log.error("Exception selecting Customer [{}]", customer.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private List<CustomerAudit> searchAudit(Customer customer) throws Exception {
		try {
			return customerAuditRepo.findByLoginName(customer.getLoginName());
		} catch (Exception e) {
			log.error("Exception selecting Customer [{}]", customer.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private Customer select(Customer customer) throws Exception {
		try {
			return customerRepo.findByCustomerKey(customer.getCustomerKey());
		} catch (Exception e) {
			log.error("Exception selecting Customer [{}]", customer.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private Customer selectByLoginNameAndPassword(Customer customer) throws Exception {
		try {
			Customer cust = customerRepo.findByLoginNameAndPassword(customer.getLoginName(), customer.getPassword());
			if (cust == null) {
				throw new Exception("Invalid User or Password");
			}
			return cust;
		} catch (Exception e) {
			log.error("Exception selecting Customer TO Login [{}]", customer.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private List<Customer> selectAll() throws Exception {
		try {
			return customerRepo.findAll();
		} catch (Exception e) {
			log.error("Exception selecting all Customer [{}]", e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private List<CustomerAudit> selectAllAudit() throws Exception {
		try {
			return customerAuditRepo.findAll();
		} catch (Exception e) {
			log.error("Exception selecting all Customer Audit [{}]", e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private Customer save(Customer customer) throws Exception {
		try {
			List<Customer> custList = customerRepo.findByLoginName(customer.getLoginName());

			if (custList == null || custList.size() == 0) {
				customer.setCustomerKey(null);

				customer = customerRepo.save(customer);
				CustomerAudit audit = convertCustomerToAudit(customer);
				audit.setActive(true);
				customerAuditRepo.save(audit);
				return customer;
			} else {
				throw new Exception("Customer already exist");
			}
		} catch (Exception e) {
			log.error("Exception saving Customer [{}]", customer.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private Customer update(Customer customer) throws Exception {
		try {
			Customer cust = null;
			if (customer.getCustomerKey() == null) {
				cust = customerRepo.findByLoginNameAndPassword(customer.getLoginName(), customer.getPassword());
				if (cust == null) {
					throw new Exception("Customer not found to update.");
				}
			} else {
				cust = customerRepo.findByCustomerKey(customer.getCustomerKey());
			}

			cust.setCustomerVer(cust.getCustomerVer() + 1);
			cust.setLoginName(customer.getLoginName());
			cust.setEmail(customer.getEmail());
			cust.setPassword(customer.getPassword());
			cust.setPhone(customer.getPhone());
			cust.setUserModKey(customer.getUserModKey());
			cust.setName(customer.getName());

			customerRepo.save(cust);
			CustomerAudit audit = convertCustomerToAudit(cust);
			customerAuditRepo.save(audit);
			return cust;

		} catch (Exception e) {
			log.error("Exception updating Customer [{}]", customer.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private Customer delete(Customer customer) throws Exception {
		try {
			customerRepo.delete(customer);

			CustomerAudit audit = convertCustomerToAudit(customer);
			audit.setActive(false);
			audit.setCustomerVer(audit.getCustomerVer() == null ? 1 : audit.getCustomerVer() + 1);
			customerAuditRepo.save(audit);

			return customer;
		} catch (Exception e) {
			log.error("Exception deleting Exam [{}]", customer.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private List<Customer> isEmptyRequest(Message<List<Customer>> msg) throws Exception {
		List<Customer> CustomerList = msg.getPayload();
		if (CustomerList == null || CustomerList.size() == 0) {
			throw new Exception("Empty Request.");
		}
		return CustomerList;
	}

	private Customer getAdminCustomer() {
		Customer customer = new Customer();

		customer.setName("admin");
		customer.setEmail("admin@gmail.com");
		customer.setPhone("01312345678");
		customer.setLoginName("admin");
		customer.setPassword("admin");
		customer.setDateModified(new Date());

		return customer;
	}

	private CustomerAudit convertCustomerToAudit(Customer cust) {
		CustomerAudit audit = new CustomerAudit();

		audit.setCustomerKey(cust.getCustomerKey());
		audit.setCustomerVer(cust.getCustomerVer());
		audit.setName(cust.getName());
		audit.setEmail(cust.getEmail());
		audit.setPhone(cust.getPhone());
		audit.setLoginName(cust.getLoginName());
		audit.setUserModKey(cust.getUserModKey());
		audit.setDateModified(new Date());
		audit.setActive(cust.isActive());
		return audit;
	}

	@SuppressWarnings("unused")
	private Customer convertCustomerToAudit(CustomerAudit cust) {
		Customer audit = new Customer();

		audit.setCustomerKey(cust.getCustomerKey());
		audit.setCustomerVer(cust.getCustomerVer());
		audit.setName(cust.getName());
		audit.setEmail(cust.getEmail());
		audit.setPhone(cust.getPhone());
		audit.setLoginName(cust.getLoginName());
		audit.setUserModKey(cust.getUserModKey());
		audit.setDateModified(cust.getDateModified());
		audit.setActive(cust.isActive());

		return audit;
	}

}
