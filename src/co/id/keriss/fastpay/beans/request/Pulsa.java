package co.id.keriss.fastpay.beans.request;





public class Pulsa {

	
	private String methodName="fastpay.pulsa";


	private String kodeproduk;
	
	
	private String nohp;
	
	private String uid;

	private String pin;

	private String ref1;
	


	public String getKodeproduk() {
		return kodeproduk;
	}
	
	public void setKodeproduk(String kodeproduk) {
		this.kodeproduk = kodeproduk;
	}
	public String getNohp() {
		return nohp;
	}

	public void setNohp(String nohp) {
		this.nohp = nohp;
	}
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPin() {
		return pin;
	}
		public void setPin(String pin) {
		this.pin = pin;
	}
	public String getRef1() {
		return ref1;
	}

	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}


	
}
