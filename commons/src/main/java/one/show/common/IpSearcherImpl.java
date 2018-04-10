package one.show.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpSearcherImpl implements IpSearcher{
	private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, List<Ip>>> dic;
	private String filePath;

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		System.out.println("ip data path = " + filePath);
	}
	public IpSearcherImpl(){
		this.dic = new ConcurrentHashMap();
		System.out.println("load ip data = " + loadIPData(this.dic));
	}
	public void init() {
		
	}

	private int[] toArray(String ip) {
		if (ip == null) {
			return null;
		}
		int[] numArr = new int[4];
		StringTokenizer st = new StringTokenizer(ip, ".");
		int index = 0;
		while (st.hasMoreTokens()) {
			numArr[(index++)] = Integer.valueOf(st.nextToken()).intValue();
		}
		return numArr;
	}

	public int reload() {
		ConcurrentHashMap ipMap = new ConcurrentHashMap();
		int count = loadIPData(ipMap);
		if (count > 0) {
			ConcurrentHashMap tmpMap = this.dic;
			this.dic = ipMap;
			tmpMap.clear();
		}
		System.out.println("load ip data = " + count);
		return count;
	}

	private int loadIPData(ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, List<Ip>>> ipMap) {
		BufferedReader br = null;
		int count = 0;
		try {
			//br = new BufferedReader(new FileReader("/Users/weipai/Downloads/ip.txt"));
			InputStream in = Loader.class.getResourceAsStream("/ip.txt");
			br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			Pattern p = Pattern.compile("([^\\s]*)\\s{1,}([^\\s]*)\\s{1,}([^\\s]*)\\s{1,}([^\\s]*)");
			Matcher m = null;

			while ((line = br.readLine()) != null) {
				m = p.matcher(line);
				if (m.find()) {
					int[] minIpArr = toArray(m.group(1));
					int[] maxIpArr = toArray(m.group(2));
					int country = Integer.valueOf(m.group(3)).intValue();
					int nettype = Integer.valueOf(m.group(4)).intValue();
					if ((minIpArr == null) || (maxIpArr == null)) {
						continue;
					}
					for (int i = minIpArr[0]; i <= maxIpArr[0]; i++) {
						ConcurrentHashMap map = (ConcurrentHashMap) ipMap.get(Integer.valueOf(i));
						if (map == null) {
							map = new ConcurrentHashMap();
							ipMap.put(Integer.valueOf(i), map);
						}
						for (int i1 = minIpArr[1]; i1 <= maxIpArr[1]; i1++) {
							List list = (List) map.get(Integer.valueOf(i1));
							if (list == null) {
								list = new ArrayList();
								map.put(Integer.valueOf(i1), list);
							}
							Ip ip = new Ip();
							ip.setMin(minIpArr[0] * 256 * 256 * 256 + minIpArr[1] * 256 * 256 + minIpArr[2] * 256 + minIpArr[3]);
							ip.setMax(maxIpArr[0] * 256 * 256 * 256 + maxIpArr[1] * 256 * 256 + maxIpArr[2] * 256 + maxIpArr[3]);
							ip.setCountry(country);
							ip.setNettype(nettype);
							list.add(ip);
						}
					}
				}
				count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				br = null;
			}
		}
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			br = null;
		}

		return count;
	}

	public String search(String ip) {
		Ip ipObj = baseSearch(ip);
		return ipObj == null ? null : ipObj.getRe();
	}

	public int searchCountry(String ip) {
		Ip ipObj = baseSearch(ip);
		return ipObj == null ? -1 : ipObj.getCountry();
	}

	public int searchNettype(String ip) {
		Ip ipObj = baseSearch(ip);
		return ipObj == null ? -1 : ipObj.getNettype();
	}

	private Ip baseSearch(String ip) {
		if (ip == null) {
			return null;
		}
		int[] numArr = toArray(ip);
		if (numArr == null) {
			return null;
		}
		ConcurrentHashMap map = (ConcurrentHashMap) this.dic.get(Integer.valueOf(numArr[0]));
		if (map == null) {
			return null;
		}
		List list = (List) map.get(Integer.valueOf(numArr[1]));
		if (list == null) {
			return null;
		}
		long num = numArr[0] * 256 * 256 * 256 + numArr[1] * 256 * 256 + numArr[2] * 256 + numArr[3];
		Iterator it = list.iterator();

		while (it.hasNext()) {
			Ip ipObj = (Ip) it.next();
			if ((ipObj.getMax() >= num) && (ipObj.getMin() <= num)) {
				return ipObj;
			}
		}
		return null;
	}
}
