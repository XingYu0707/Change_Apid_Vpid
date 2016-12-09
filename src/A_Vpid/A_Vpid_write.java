package A_Vpid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class A_Vpid_write {

	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
	private byte[] m_datapadding = { 0x00 }; //���հף��Բ����ֽ�λ��.
	public A_Vpid_write(String s_FilePath) {
		// TODO Auto-generated constructor stub
		init(s_FilePath);
	}

	private void init(String s_FilePath) {
		try {
			if (!new File(s_FilePath).exists()) {
				new File(s_FilePath).createNewFile();
			}
			dis = new DataInputStream(new FileInputStream(new File(s_FilePath)));
			dos = new DataOutputStream(new FileOutputStream(new File(s_FilePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean writeBinaryStream(String fileName, int Vpid, int Apid, int newVpid, int newApid,int Pmtpid) {
		File file = new File(fileName);
		final int MAX = 150000000;
		if (file.exists()) {
		
		if (MAX< (((int)file.length())/4)) {
			System.out.println("�ļ�����,ת��ʧ�ܣ�");
			return false;
		}
		System.out.println("�ļ�����:" + file.length() + "�ֽ�"+"  "+file.length()/4+"int");

		byte[] bytes= A_Vpid_read.readFile(fileName,Vpid,Apid,newVpid,newApid,file.length(),file,Pmtpid);
		/*byte[] bytes = new byte[num];
		bytes=byt;*/
		try {
			if (dos != null) {
				 /*//for(int i=0;i<2;i++){  
					 for(int j=0;j<=bytes.length-1;j++){
							bytes[j]=(byte) Integer.parseInt(bytes[j]);
						}*/
					 
					 dos.write(bytes);
					 
					 String cs="c";     
	                 
					 dos.writeUTF(cs);   
	                 
					 dos.writeUTF(cs);   
	                 
					 dos.write(m_datapadding);  
	                //}
					//dos.flush();
					dos.close();
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
		
		}
		else {
			System.out.println("�ļ������ڣ�����");
			return false;
		}
		
	}

	public void readBinaryStream() {
		try {
			if (dis != null) {
				while (dis.available() > 0) {					
					System.out.println(dis.readInt());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("���趨�޸�pid���ts�ļ���·���Լ��ļ��������磺E://1.ts");
		String s_FilePath = scan.next();
		//String s_FilePath = "E://��èת��//�½��ļ���//Gee1.1.ts";//���ɵ�ts�ļ�
		A_Vpid_write bin = new A_Vpid_write(s_FilePath);
		System.out.println("������ԭʼҪ�޸�pid��ts�ļ���·���Լ��ļ��������磺E://1.ts��ע���ļ�����С��200M���ұ�֤û�г�������ʹ�ø���������");
		String f_Filepath = scan.next();
		System.out.println("������ԭʼ�ļ�Ҫ�޸�����Ƶ��Ӧ��PMT PID��ʮ���ƣ���");
		int Pmtpid = scan.nextInt();
		System.out.println("������ԭʼ�ļ�����Ƶpid��ʮ���ƣ���");
		int V_pid = scan.nextInt();
		System.out.println("������ԭʼ�ļ�����Ƶpid��ʮ���ƣ���");
		int A_pid = scan.nextInt();
		System.out.println("���趨�µ���Ƶpid��ʮ���ƣ���");
		int NV_pid = scan.nextInt();
		System.out.println("���趨�µ���Ƶpid��ʮ���ƣ���");
		int NA_pid = scan.nextInt();
		System.out.println("ת���У����Ժ�....");
		System.out.println(bin.writeBinaryStream(f_Filepath, V_pid, A_pid, NV_pid, NA_pid,Pmtpid));//��ȡ��ts�ļ�
		System.out.println("");
		System.out.println("ת����ɣ�5����Զ��˳�");
		try {
			// ��ʱ 30 ��
			Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		//bin.writeBinaryStream(f_Filepath, 256, 257, 356, 357,200000000);//��ȡ��ts�ļ�
	}


}
