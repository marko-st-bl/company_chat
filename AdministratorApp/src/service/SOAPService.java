package service;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.axis2.AxisFault;

import com.marko.service.AdministratorServiceStub;

public class SOAPService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SOAPService.class.getName());

	public static boolean createAccount(String firstName, String lastName, String username, String password) {
		try {
			AdministratorServiceStub stub = new AdministratorServiceStub();

			AdministratorServiceStub.CreateAccount request = new AdministratorServiceStub.CreateAccount();
			request.setFirstName(firstName);
			request.setLastName(lastName);
			request.setUsername(username);
			request.setPassword(password);

			AdministratorServiceStub.CreateAccountResponse response = stub.createAccount(request);
			boolean res = response.get_return();
			return res;
		} catch (AxisFault e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (RemoteException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}

	public static boolean blockUser(String id) {
		try {
		AdministratorServiceStub stub=new AdministratorServiceStub();
		
		AdministratorServiceStub.BlockUser request=new AdministratorServiceStub.BlockUser();
		request.setId(id);
		
		AdministratorServiceStub.BlockUserResponse response=stub.blockUser(request);
		boolean res = response.get_return();
		return res;
		}catch (AxisFault e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (RemoteException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}

}
