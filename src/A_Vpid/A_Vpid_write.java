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
	
	private byte[] m_datapadding = { 0x00 }; //填充空白，以补足字节位数.
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
			System.out.println("文件过大,转换失败！");
			return false;
		}
		System.out.println("文件存在:" + file.length() + "字节"+"  "+file.length()/4+"int");

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
			System.out.println("文件不存在！！！");
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
		System.out.println("请设定修改pid后的ts文件的路径以及文件名。例如：E://1.ts");
		String s_FilePath = scan.next();
		//String s_FilePath = "E://狸猫转换//新建文件夹//Gee1.1.ts";//生成的ts文件
		A_Vpid_write bin = new A_Vpid_write(s_FilePath);
		System.out.println("请输入原始要修改pid的ts文件的路径以及文件名。例如：E://1.ts（注：文件建议小于200M，且保证没有程序正在使用该码流。）");
		String f_Filepath = scan.next();
		System.out.println("请输入原始文件要修改音视频对应的PMT PID（十进制）：");
		int Pmtpid = scan.nextInt();
		System.out.println("请输入原始文件的视频pid（十进制）：");
		int V_pid = scan.nextInt();
		System.out.println("请输入原始文件的音频pid（十进制）：");
		int A_pid = scan.nextInt();
		System.out.println("请设定新的视频pid（十进制）：");
		int NV_pid = scan.nextInt();
		System.out.println("请设定新的音频pid（十进制）：");
		int NA_pid = scan.nextInt();
		System.out.println("转换中，请稍后....");
		System.out.println(bin.writeBinaryStream(f_Filepath, V_pid, A_pid, NV_pid, NA_pid,Pmtpid));//读取的ts文件
		System.out.println("");
		System.out.println("转换完成，5秒后自动退出");
		try {
			// 延时 30 秒
			Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		//bin.writeBinaryStream(f_Filepath, 256, 257, 356, 357,200000000);//读取的ts文件
	}


}
