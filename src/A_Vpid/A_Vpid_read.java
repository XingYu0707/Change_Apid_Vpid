package A_Vpid;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class A_Vpid_read {

	public static byte[] readFile(String fileName, int Vpid, int Apid, int newVpid, int newApid, long num, File file, int Pmtpid) {

		byte[] itemBuf=new byte[(int) num];

			try {
				FileInputStream in = new FileInputStream(file);
				DataInputStream dis = new DataInputStream(in);
				dis.read(itemBuf, 0, (int) file.length());
				int n = 0;
				int s = 0;
				int ts = 0;
				int count = 0;
				int m = 1;

				for (count = 0; count >= 0; count++) {// 判断第一个ts包的位置

					if (itemBuf[s] == 71 && itemBuf[s + 188] == 71 && itemBuf[s + 188 + 188] == 71 && itemBuf[s + 188 + 188 + 188] == 71) {
						break;
					}
				}

				pat: for (int j = count; j < itemBuf.length; j = j + 188) { //
					s = j;
					n = n + 1;
					ts = ts + 1;
					//PID
					String s2 = Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 2] & 0x000000ff), 16));// 十六进制转二进制，计算pid
					if (s2.length() < 8) {
						int dl = 8 - s2.length();
						for (int i = 1; i <= dl; i++) {
							s2 = "0" + s2;
						}
					}
					String s1 = Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 1] & 0x000000ff), 16));
					String b = s1 + s2;// 十六进制转二进制，计算pid
					String c = b;
					if (b.length() < 16) {// 判断有无首位十六进制省略
						for (int i = 1; i <= 16 - b.length(); i++) {
							c = "0" + c;
						}
					}
					String Truepid = "000" + c.substring(c.length() - 13, c.length());// 截取后十三位pid
					String Hexpid = Long.toHexString(Long.parseLong(Truepid, 2));// 二进制转十六进制
					// Pcrpid
					String Pcrpid1 = Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 13] & 0x000000ff), 16));
					String Pcrpid2 = Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 14] & 0x000000ff), 16));
					if (Pcrpid2.length() < 8) {
						int dl = 8 - Pcrpid2.length();
						for (int i = 1; i <= dl; i++) {
							Pcrpid2 = "0" + Pcrpid2;
						}
					}
					String Pcrpid = Pcrpid1+Pcrpid2;
					if (Pcrpid.length() < 16) {
						int dl = 16 - Pcrpid.length();
						for (int i = 1; i <= dl; i++) {
							Pcrpid = "0" + Pcrpid;
						}
					}
					// CA descriptors
					String CAdes1 = Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 15] & 0x000000ff), 16));
					String CAdes2 = Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 16] & 0x000000ff), 16));
					if (CAdes2.length() < 8) {
						int dl = 8 - CAdes2.length();
						for (int i = 1; i <= dl; i++) {
							CAdes2 = "0" + CAdes2;
						}
					}
					String CAdes = CAdes1+CAdes2;
					if (CAdes.length() < 16) {
						int dl = 16 - CAdes.length();
						for (int i = 1; i <= dl; i++) {
							CAdes = "0" + CAdes;
						}
					}
					CAdes = "0000" + CAdes.substring(4, 16);
					//System.out.println("CAdes:" + CAdes);
					String HexCAdes = Long.toHexString(Long.parseLong(CAdes, 2));// 二进制转十六进制
					
					
					if (Integer.valueOf(Hexpid, 16) == Vpid) {
						String BinnewVpid = Long.toBinaryString(Long.parseLong(Integer.toHexString(newVpid), 16));// 新视频pid转2进制
						if (BinnewVpid.length() < 16) {// 判断有无首位十六进制省略
							int B = BinnewVpid.length();
							for (int i = 1; i <= 16 - B; i++) {
								BinnewVpid = "0" + BinnewVpid;
							}
						}
						String allPid = c.substring(0, 3) + BinnewVpid.substring(c.length() - 13, c.length());// 原前三位二进制与新后十三位pid之和组成新的十六位二进制
						String Hexallpid = Integer.toHexString(Integer.valueOf(allPid, 2));// 十六位2进制转四位16进制
						if (Hexallpid.length() < 4) {// 判断有无首位十六进制省略
							int B = Hexallpid.length();
							for (int i = 1; i <= 4 - B; i++) {
								Hexallpid = "0" + Hexallpid;
							}
						}
						
						String byt= Hexallpid.substring(0, 2);
						byte[] bytes ={(byte) Integer.parseInt(byt, 16)};
						itemBuf[s + 1]=bytes[0];
						String byt1 =Hexallpid.substring(2, 4);;
						byte[] bytes1 ={(byte) Integer.parseInt(byt1, 16)};
						itemBuf[s + 2]=bytes1[0];

					}
					if (Integer.valueOf(Hexpid, 16) == Apid) {
						String BinnewApid = Long.toBinaryString(Long.parseLong(Integer.toHexString(newApid), 16));// 新视频pid转2进制
						if (BinnewApid.length() < 16) {// 判断有无首位十六进制省略
							int B = BinnewApid.length();
							for (int i = 1; i <= 16 - B; i++) {
								BinnewApid = "0" + BinnewApid;
							}
						}
						String allPid = c.substring(0, 3) + BinnewApid.substring(c.length() - 13, c.length());// 原前三位二进制与新后十三位pid之和组成新的十六位二进制
						String Hexallpid = Integer.toHexString(Integer.valueOf(allPid, 2));// 十六位2进制转四位16进制
						if (Hexallpid.length() < 4) {// 判断有无首位十六进制省略
							int B = Hexallpid.length();
							for (int i = 1; i <= 4 - B; i++) {
								Hexallpid = "0" + Hexallpid;
							}
						}
						
						String byt= Hexallpid.substring(0, 2);
						byte[] bytes ={(byte) Integer.parseInt(byt, 16)};
						itemBuf[s + 1]=bytes[0];
						String byt1 =Hexallpid.substring(2, 4);;
						byte[] bytes1 ={(byte) Integer.parseInt(byt1, 16)};
						itemBuf[s + 2]=bytes1[0];

					}
					if (Integer.valueOf(Hexpid, 16) == Pmtpid) {
						String BinnewVpid = Long.toBinaryString(Long.parseLong(Integer.toHexString(newVpid), 16));// 新视频pid转2进制
						if (BinnewVpid.length() < 16) {// 判断有无首位十六进制省略
							int B = BinnewVpid.length();
							for (int i = 1; i <= 16 - B; i++) {
								BinnewVpid = "0" + BinnewVpid;
							}
						}
						String NewPcrpid = Pcrpid.substring(0, 3) + BinnewVpid.substring(Pcrpid.length() - 13, c.length());// 原前三位二进制与新后十三位pid之和组成新的十六位二进制
						String HexNewPcrpid = Integer.toHexString(Integer.valueOf(NewPcrpid, 2));// 十六位2进制转四位16进制
						if (HexNewPcrpid.length() < 4) {// 判断有无首位十六进制省略
							int B = HexNewPcrpid.length();
							for (int i = 1; i <= 4 - B; i++) {
								HexNewPcrpid = "0" + HexNewPcrpid;
							}
						}
						
						String byt= HexNewPcrpid.substring(0, 2);
						byte[] bytes ={(byte) Integer.parseInt(byt, 16)};
						itemBuf[s + 13]=bytes[0];
						String byt1 =HexNewPcrpid.substring(2, 4);
						byte[] bytes1 ={(byte) Integer.parseInt(byt1, 16)};
						itemBuf[s + 14]=bytes1[0];
						
						
						int CAdessize = Integer.valueOf(HexCAdes, 16);
						String PmtFirstpid1=Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 18 + CAdessize] & 0x000000ff), 16));
						String PmtFirstpid2=Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 19 + CAdessize] & 0x000000ff), 16));
						if (PmtFirstpid2.length() < 8) {
							int dl = 8 - PmtFirstpid2.length();
							for (int i = 1; i <= dl; i++) {
								PmtFirstpid2 = "0" + PmtFirstpid2;
							}
						}
						String Firstpid = PmtFirstpid1+PmtFirstpid2;
						if (Firstpid.length() < 16) {// 判断有无首位十六进制省略
							int B = Firstpid.length();
							for (int i = 1; i <= 16 - B; i++) {
								Firstpid = "0" + Firstpid;
							}
						}
						Firstpid="000"+Firstpid.substring(3, 16);
						byte[] Firstpid1=Firstpid(Firstpid,Vpid,Apid,newVpid,newApid);
						itemBuf[s + 18 + CAdessize]=Firstpid1[0];
						itemBuf[s + 19 + CAdessize]=Firstpid1[1];
						//ES Lengh descriptors
						String ESdes1 = Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 20 + CAdessize] & 0x000000ff), 16));
						String ESdes2 = Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 21 + CAdessize] & 0x000000ff), 16));
						if (ESdes2.length() < 8) {
							int dl = 8 - ESdes2.length();
							for (int i = 1; i <= dl; i++) {
								ESdes2 = "0" + ESdes2;
							}
						}
						String ESdes = ESdes1+ESdes2;
						if (ESdes.length() < 16) {
							int dl = 16 - ESdes.length();
							for (int i = 1; i <= dl; i++) {
								ESdes = "0" + ESdes;
							}
						}
						ESdes = "0000" + ESdes.substring(4, 16);
						//System.out.println("ESdes:" + ESdes);
						String HexESdes = Long.toHexString(Long.parseLong(ESdes, 2));// 二进制转十六进制
						
						int ESdessize = Integer.valueOf(HexESdes, 16);
						
						String PmtSecondpid1=Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 23 + CAdessize + ESdessize] & 0x000000ff), 16));
						String PmtSecondpid2=Long.toBinaryString(Long.parseLong(Integer.toHexString(itemBuf[s + 24 + CAdessize + ESdessize] & 0x000000ff), 16));
						if (PmtSecondpid2.length() < 8) {
							int dl = 8 - PmtSecondpid2.length();
							for (int i = 1; i <= dl; i++) {
								PmtSecondpid2 = "0" + PmtSecondpid2;
							}
						}
						String Secondpid = PmtSecondpid1+PmtSecondpid2;
						if (Secondpid.length() < 16) {// 判断有无首位十六进制省略
							int B = Secondpid.length();
							for (int i = 1; i <= 16 - B; i++) {
								Secondpid = "0" + Secondpid;
							}
						}
						Secondpid="000"+Secondpid.substring(3, 16);
						
						byte[] Secondpid1=Secondpid(Secondpid,Vpid, Apid,newVpid,newApid);
						itemBuf[s + 23 + CAdessize + ESdessize]=Secondpid1[0];
						itemBuf[s + 24 + CAdessize + ESdessize]=Secondpid1[1];
					}
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// close
			}
		
		return itemBuf;
	}
	public static byte[] Firstpid(String Firstpid,int Vpid, int Apid,int newVpid,int newApid) {
		byte[] itemBuf=new byte[2];
		String HexFirstpid = Long.toHexString(Long.parseLong(Firstpid, 2));// 二进制转十六进制
		
		int PmtFirstpidint = Integer.valueOf(HexFirstpid, 16);//PmtVpidint十进制真实的pid
		if (PmtFirstpidint==Vpid) {
			String BinnewPmtVpid = Long.toBinaryString(Long.parseLong(Integer.toHexString(newVpid), 16));// 新视频pid转2进制
			if (BinnewPmtVpid.length() < 16) {// 判断有无首位十六进制省略
				int B = BinnewPmtVpid.length();
				for (int i = 1; i <= 16 - B; i++) {
					BinnewPmtVpid = "0" + BinnewPmtVpid;
				}
			}
			String NewPmtVpid = Firstpid.substring(0, 3)+ BinnewPmtVpid.substring(3, 16);
			String HexNewPmtVpid = Integer.toHexString(Integer.valueOf(NewPmtVpid, 2));
			if (HexNewPmtVpid.length() < 4) {// 判断有无首位十六进制省略
				int B = HexNewPmtVpid.length();
				for (int i = 1; i <= 4 - B; i++) {
					HexNewPmtVpid = "0" + HexNewPmtVpid;
				}
			}
			String bytPmtVpid= HexNewPmtVpid.substring(0, 2);
			byte[] bytesPmtVpid ={(byte) Integer.parseInt(bytPmtVpid, 16)};
			itemBuf[0]=bytesPmtVpid[0];
			String bytPmtVpid1 =HexNewPmtVpid.substring(2, 4);
			byte[] bytesPmtVpid1 ={(byte) Integer.parseInt(bytPmtVpid1, 16)};
			itemBuf[1]=bytesPmtVpid1[0];
		} else if(PmtFirstpidint==Apid){
			String BinnewPmtApid = Long.toBinaryString(Long.parseLong(Integer.toHexString(newApid), 16));// 新视频pid转2进制
			if (BinnewPmtApid.length() < 16) {// 判断有无首位十六进制省略
				int B = BinnewPmtApid.length();
				for (int i = 1; i <= 16 - B; i++) {
					BinnewPmtApid = "0" + BinnewPmtApid;
				}
			}
			String NewPmtApid = Firstpid.substring(0, 3)+ BinnewPmtApid.substring(3, 16);
			//System.out.println("PmtVpid:"+NewPmtVpid+"  BinnewPmtVpid:"+BinnewPmtVpid);
			String HexNewPmtApid = Integer.toHexString(Integer.valueOf(NewPmtApid, 2));
			if (HexNewPmtApid.length() < 4) {// 判断有无首位十六进制省略
				int B = HexNewPmtApid.length();
				for (int i = 1; i <= 4 - B; i++) {
					HexNewPmtApid = "0" + HexNewPmtApid;
				}
			}
			String bytPmtApid= HexNewPmtApid.substring(0, 2);
			byte[] bytesPmtApid ={(byte) Integer.parseInt(bytPmtApid, 16)};
			itemBuf[0]=bytesPmtApid[0];
			String bytPmtApid1 =HexNewPmtApid.substring(2, 4);
			byte[] bytesPmtApid1 ={(byte) Integer.parseInt(bytPmtApid1, 16)};
			itemBuf[1]=bytesPmtApid1[0];
		}
		return itemBuf;
		
	}
	public static byte[] Secondpid(String Secondpid,int Vpid, int Apid,int newVpid,int newApid) {
		byte[] itemBuf=new byte[2];
		String HexSecondpid = Long.toHexString(Long.parseLong(Secondpid, 2));// 二进制转十六进制
		int PmtSecondpidint = Integer.valueOf(HexSecondpid, 16);//PmtVpidint十进制真实的pid
		if (PmtSecondpidint==Vpid) {
			String BinnewPmtSecondpid = Long.toBinaryString(Long.parseLong(Integer.toHexString(newVpid), 16));// 新视频pid转2进制
			if (BinnewPmtSecondpid.length() < 16) {// 判断有无首位十六进制省略
				int B = BinnewPmtSecondpid.length();
				for (int i = 1; i <= 16 - B; i++) {
					BinnewPmtSecondpid = "0" + BinnewPmtSecondpid;
				}
			}
			String NewPmtSecondpid = Secondpid.substring(0, 3)+ BinnewPmtSecondpid.substring(3, 16);
			String HexNewPmtSecondpid = Integer.toHexString(Integer.valueOf(NewPmtSecondpid, 2));
			if (HexNewPmtSecondpid.length() < 4) {// 判断有无首位十六进制省略
				int B = HexNewPmtSecondpid.length();
				for (int i = 1; i <= 4 - B; i++) {
					HexNewPmtSecondpid = "0" + HexNewPmtSecondpid;
				}
			}
			String bytPmtSecondpid= HexNewPmtSecondpid.substring(0, 2);
			byte[] bytesPmtSecondpid ={(byte) Integer.parseInt(bytPmtSecondpid, 16)};
			itemBuf[0]=bytesPmtSecondpid[0];
			String bytPmtSecondpid1 =HexNewPmtSecondpid.substring(2, 4);
			byte[] bytesPmtSecondpid1 ={(byte) Integer.parseInt(bytPmtSecondpid1, 16)};
			itemBuf[1]=bytesPmtSecondpid1[0];
		} else if(PmtSecondpidint==Apid){
			String BinnewPmtSecondpid = Long.toBinaryString(Long.parseLong(Integer.toHexString(newApid), 16));// 新视频pid转2进制
			if (BinnewPmtSecondpid.length() < 16) {// 判断有无首位十六进制省略
				int B = BinnewPmtSecondpid.length();
				for (int i = 1; i <= 16 - B; i++) {
					BinnewPmtSecondpid = "0" + BinnewPmtSecondpid;
				}
			}
			String NewPmtSecondpid = Secondpid.substring(0, 3)+ BinnewPmtSecondpid.substring(3, 16);
			String HexNewPmtSecondpid = Integer.toHexString(Integer.valueOf(NewPmtSecondpid, 2));
			if (HexNewPmtSecondpid.length() < 4) {// 判断有无首位十六进制省略
				int B = HexNewPmtSecondpid.length();
				for (int i = 1; i <= 4 - B; i++) {
					HexNewPmtSecondpid = "0" + HexNewPmtSecondpid;
				}
			}
	
			String bytPmtSecondpid= HexNewPmtSecondpid.substring(0, 2);
			byte[] bytesPmtSecondpid ={(byte) Integer.parseInt(bytPmtSecondpid, 16)};
			itemBuf[0]=bytesPmtSecondpid[0];
			String bytPmtSecondpid1 =HexNewPmtSecondpid.substring(2, 4);
			byte[] bytesPmtSecondpid1 ={(byte) Integer.parseInt(bytPmtSecondpid1, 16)};
			itemBuf[1]=bytesPmtSecondpid1[0];
		}
		
		return itemBuf;
	}
	
}
