package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileTransferI extends Remote{
	
	public List<String> getUserFiles(String id) throws RemoteException;
	public byte[] downloadFile(String name,String id) throws RemoteException;
	public boolean sendFile(byte[] data,String name,String id) throws RemoteException;

}
