package var3d.net.center.json;


/**
 * 基于Json增加的简化操作的类
 * 
 * @author Var3D
 * 
 */
public class VJSON {
	private JSONObject jsonObj = null;

	/**
	 * 初始化
	 */
	public VJSON() {
		this("");
	}

	/**
	 * 初始化,json字符串转换为json对象
	 * 
	 * @param jsonStr
	 */
	public VJSON(String jsonStr) {
		if (jsonStr.equals(""))
			jsonStr = "{}";
		try {
			jsonObj = new JSONObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// public VJSON(String fileName, String key) {
	// FileHandle file = new FileHandle(fileName);
	// String jsonStr = file.readString();
	// if (key != null)
	// jsonStr = decrypt(jsonStr, key);
	// if (jsonStr.equals(""))
	// jsonStr = "{}";
	// try {
	// jsonObj = new JSONObject(jsonStr);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 获取json路径下的某个元素(如果路径不存在返回null值)
	 * 
	 * @param jsonPath
	 *            例如"rpg/man/hp",rpg和man为jsonObject
	 */
	public Object get(String jsonPath) {
		jsonPath = jsonPath.replaceAll("\\.", "/");
		String[] paths = jsonPath.split("/");
		Object o = null;
		JSONObject object = jsonObj;
		try {
			for (String name : paths) {
				o = object.get(name);
				if (o instanceof JSONObject)
					object = (JSONObject) o;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 获取json路径下的某个元素(如果路径不存在返回null值,并自动创建路径)
	 * 
	 * @param jsonPath
	 *            例如"rpg/man/hp",rpg和man为jsonObject
	 */
	public Object getIt(String jsonPath) {
		jsonPath = jsonPath.replaceAll("\\.", "/");
		String[] paths = jsonPath.split("/");
		Object o = null;
		JSONObject object = jsonObj;
		try {
			for (String name : paths) {
				if (object.isNull(name))
					object.put(name, new JSONObject());
				o = object.get(name);
				if (o instanceof JSONObject)
					object = (JSONObject) o;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 新增，修改，删除(修改所在路径的值，如果不存在则自动创建路径。如果值为null则为删除)
	 * 
	 * @param jsonPath
	 * @param vaule
	 */
	public void put(String jsonPath, Object vaule) {
		jsonPath = jsonPath.replaceAll("\\.", "/");
		int id = jsonPath.lastIndexOf("/");
		String key = jsonPath;
		JSONObject object = jsonObj;
		if (id != -1) {
			key = jsonPath.substring(id + 1);
			jsonPath = jsonPath.substring(0, id);
			object = (JSONObject) getIt(jsonPath);
		}
		try {
			object.put(key, vaule);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改节点名称
	 * 
	 * @param jsonPath
	 * @param nodeName
	 */
	public void edit(String jsonPath, String nodeName) {
		jsonPath = jsonPath.replaceAll("\\.", "/");
		int id = jsonPath.lastIndexOf("/");
		String key = jsonPath;
		JSONObject object = jsonObj;
		if (id != -1) {
			key = jsonPath.substring(id + 1);
			jsonPath = jsonPath.substring(0, id);
			object = (JSONObject) getIt(jsonPath);
		}
		try {
			object.put(nodeName, object.get(key));
			object.put(key, null);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取指定节点元素个数
	 * 
	 * @param jsonPath
	 * @return
	 */
	public int length(String jsonPath) {
		Object object = get(jsonPath);
		if (object instanceof JSONObject)
			return ((JSONObject) object).length();
		if (object instanceof JSONArray)
			return ((JSONArray) object).length();
		return 0;
	}

	/**
	 * 转换为json字符串
	 * 
	 * @return
	 */
	public String toStirng() {
		return jsonObj.toString();
	}

	// /**
	// * 保存json到指定路径
	// *
	// * @param fileName
	// */
	// public void savePath(String fileName) {
	// FileHandle file = new FileHandle(fileName);
	// file.writeString(jsonObj.toString(), false);
	// }
	//
	// /**
	// * 保存json到指定路径并加密
	// *
	// * @param fileName
	// * @param encryptKey密码
	// */
	// public void savePath(String fileName, String encryptKey) {
	// FileHandle file = new FileHandle(fileName);
	// file.writeString(encrypt(encryptKey), false);
	// }
	//
	// /**
	// * 保存json到私有路径
	// *
	// * @param name
	// */
	// public void save(String name) {
	// byte[] buf = jsonObj.toString().getBytes();
	// StringBuffer sb = new StringBuffer();
	// for (byte b : buf) {
	// sb.append(String.format("%02X ", b));
	// }
	// buf = sb.toString().getBytes();
	// Preferences safe = Gdx.app.getPreferences(name);
	// safe.putString(name, sb.toString());
	// safe.flush();
	// }
	//
	// /**
	// * 根据参数生成KEY
	// */
	// public Key getKey(String strKey) {
	// try {
	// KeyGenerator _generator = KeyGenerator.getInstance("DES");
	// _generator.init(new SecureRandom(strKey.getBytes()));
	// Key key = _generator.generateKey();
	// _generator = null;
	// return key;
	// } catch (Exception e) {
	// throw new RuntimeException(
	// "Error initializing SqlMap class. Cause: " + e);
	// }
	// }
	//
	// /**
	// * 将json字符串加密
	// */
	// public String encrypt(String key) {
	// String outStr = null;
	// try {
	// Cipher cipher = Cipher.getInstance("DES");
	// cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
	// InputStream is = new FileInputStream(jsonObj.toString());
	// CipherInputStream cis = new CipherInputStream(is, cipher);
	// outStr = cis.toString();
	// cis.close();
	// is.close();
	// } catch (NoSuchAlgorithmException e) {
	// e.printStackTrace();
	// } catch (NoSuchPaddingException e) {
	// e.printStackTrace();
	// } catch (InvalidKeyException e) {
	// e.printStackTrace();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return outStr;
	// }
	//
	// /**
	// * 文件采用DES算法解密文件
	// *
	// * @param file
	// * 已加密的文件 如c:/加密后文件.txt * @param destFile 解密后存放的文件名 如c:/
	// * test/解密后文件.txt
	// */
	// public String decrypt(String file, String key) {
	// Cipher cipher = Cipher.getInstance("DES");
	// cipher.init(Cipher.DECRYPT_MODE, getKey(key));
	// InputStream is = new FileInputStream(file);
	// OutputStream out = new FileOutputStream(dest);
	// CipherOutputStream cos = new CipherOutputStream(out, cipher);
	// byte[] buffer = new byte[1024];
	// int r;
	// while ((r = is.read(buffer)) >= 0) {
	// System.out.println();
	// cos.write(buffer, 0, r);
	// }
	// cos.close();
	// out.close();
	// is.close();
	// }
}
