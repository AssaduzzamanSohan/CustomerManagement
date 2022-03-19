package com.example.customer.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.customer.constants.ActionType;
import com.example.customer.message.MessageBuilder;
import com.example.customer.message.MessageHeader;
import com.example.customer.message.interfaces.Message;
import com.example.customer.model.User;
import com.example.customer.repository.UserRepository;

public class UserService extends AbstractService<User> {
	private static Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	UserRepository userRepo;

	@PostConstruct
	public void init() {
		User admin = getAdminUser();
		User general = getGeneralUser();
		try {
			User adminInDB = userRepo.findByLoginNameAndPassword(admin.getLoginName(), admin.getPassword());
			if (adminInDB == null) {
				save(admin);
			}
		} catch (Exception e) {
			log.error("Exception inserting Admin User", e);
		}
		try {
			User generalInDB = userRepo.findByLoginNameAndPassword(general.getLoginName(), general.getPassword());
			if (generalInDB == null) {
				save(general);
			}
		} catch (Exception e) {
			log.error("Exception inserting General User", e);
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

			List<User> userList = isEmptyRequest(msg);

			if (action.equals(ActionType.NEW.toString())) {
				msgResponse = MessageBuilder.withPayload(save(userList.get(0))).copyHeadersIfAbsent(msgHeader).build();
			} else if (action.equals(ActionType.SELECT.toString())) {
				msgResponse = MessageBuilder.withPayload(select(userList.get(0))).copyHeadersIfAbsent(msgHeader)
						.build();
			} else if (action.equals(ActionType.SELECT_ALL.toString())) {
				msgResponse = MessageBuilder.withPayload(selectAll()).copyHeadersIfAbsent(msgHeader).build();
			} else if (action.equals(ActionType.UPDATE.toString())) {
				msgResponse = MessageBuilder.withPayload(update(userList.get(0))).copyHeadersIfAbsent(msgHeader)
						.build();
			} else if (action.equals(ActionType.DELETE.toString())) {
				msgResponse = MessageBuilder.withPayload(delete(userList.get(0))).copyHeadersIfAbsent(msgHeader)
						.build();
			} else if (action.equals(ActionType.LOGIN.toString())) {
				msgResponse = MessageBuilder.withPayload(selectByLoginNameAndPassword(userList.get(0)))
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

	private User select(User user) throws Exception {
		try {
			return userRepo.findByUserKey(user.getUserKey());
		} catch (Exception e) {
			log.error("Exception selecting user [{}]", user.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private User selectByLoginNameAndPassword(User user) throws Exception {
		try {
			User u = userRepo.findByLoginNameAndPassword(user.getLoginName(), user.getPassword());
			if (u == null) {
				throw new Exception("Invalid User or Password");
			}
			return u;
		} catch (Exception e) {
			log.error("Exception selecting user TO Login [{}]", user.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private List<User> selectAll() throws Exception {
		try {
			return userRepo.findAll();
		} catch (Exception e) {
			log.error("Exception selecting all Exam [{}]", e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private User save(User user) throws Exception {
		try {
			return userRepo.save(user);
		} catch (Exception e) {
			log.error("Exception saving User [{}]", user.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private User update(User user) throws Exception {
		try {
			if (user.getUserKey() == null) {
				throw new Exception("User key not found in Object, First Find then try to update");
			}
			return userRepo.save(user);
		} catch (Exception e) {
			log.error("Exception updating User [{}]", user.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private User delete(User user) throws Exception {
		try {
			userRepo.delete(user);
			return user;
		} catch (Exception e) {
			log.error("Exception deleting Exam [{}]", user.toString(), e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	private List<User> isEmptyRequest(Message<List<User>> msg) throws Exception {
		List<User> userList = msg.getPayload();
		if (userList == null || userList.size() == 0) {
			throw new Exception("Empty Request.");
		}
		return userList;
	}

	private User getAdminUser() {
		User user = new User();

		user.setName("admin");
		user.setLoginName("admin");
		user.setPassword("admin");
		user.setDisabled(false);
		user.setAllowLogin(true);
		user.setDateModified(new Date());
		user.setRoles("ADMIN");
		user.setComment("This is a system generated admin.");

		return user;
	}

	private User getGeneralUser() {
		User user = new User();

		user.setName("general");
		user.setLoginName("general");
		user.setPassword("general");
		user.setDisabled(false);
		user.setAllowLogin(true);
		user.setDateModified(new Date());
		user.setRoles("USER");
		user.setComment("This is a system generated User.");

		return user;
	}

}
