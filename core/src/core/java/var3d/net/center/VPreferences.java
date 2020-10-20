package var3d.net.center;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.Map;

import var3d.net.center.json.JSONObject;
import var3d.net.center.tool.DesUtils;

/**
 * Created by yufeng on 2019/12/31.
 */

public class VPreferences {
    private Preferences save;
    private HashMap<String, DesUtils> arrayKeys = new HashMap<>();
    private String packName;
    private JSONObject object = new JSONObject();

    public VPreferences(String name) {
        this.packName = name;
        save = Gdx.app.getPreferences(name + ".new");// 数据存储实例化
    }

    private DesUtils getKeyStore(String key) {
        DesUtils keyStore = arrayKeys.get(key);
        if (keyStore == null) {
            keyStore = new DesUtils(packName + key);
            arrayKeys.put(key, keyStore);
        }
        return keyStore;
    }

    public VPreferences putBoolean(String key, boolean val) {
        try {
            DesUtils desUtils = getKeyStore(key);
            object.put(key, val);
            save.putString(key, desUtils.encrypt(object.toString()));
            object.remove(key);
        } catch (Exception e) {
            save.putString(key, object.toString());
        }
        return this;
    }

    public VPreferences putInteger(String key, int val) {
        try {
            DesUtils desUtils = getKeyStore(key);
            object.put(key, val);
            save.putString(key, desUtils.encrypt(object.toString()));
            object.remove(key);
        } catch (Exception e) {
            save.putInteger(key, val);
        }
        return this;
    }

    public VPreferences putLong(String key, long val) {
        try {
            DesUtils desUtils = getKeyStore(key);
            object.put(key, val);
            save.putString(key, desUtils.encrypt(object.toString()));
            object.remove(key);
        } catch (Exception e) {
            save.putLong(key, val);
        }
        return this;
    }

    public VPreferences putFloat(String key, float val) {
        try {
            DesUtils desUtils = getKeyStore(key);
            object.put(key, val);
            save.putString(key, desUtils.encrypt(object.toString()));
            object.remove(key);
        } catch (Exception e) {
            save.putFloat(key, val);
        }
        return this;
    }

    public VPreferences putString(String key, String val) {
        try {
            DesUtils desUtils = getKeyStore(key);
            object.put(key, val);
            save.putString(key, desUtils.encrypt(object.toString()));
            object.remove(key);
        } catch (Exception e) {
            save.putString(key, val);
        }
        return this;
    }

    public VPreferences put(Map<String, ?> vals) {
        save.put(vals);
        return this;
    }

    public boolean getBoolean(String key) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getBoolean(key);
        } catch (Exception e) {
            return save.getBoolean(key);
        }
    }

    public int getInteger(String key) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getInt(key);
        } catch (Exception e) {
            return save.getInteger(key);
        }
    }

    public long getLong(String key) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getLong(key);
        } catch (Exception e) {
            return save.getLong(key);
        }
    }

    public float getFloat(String key) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getFloat(key);
        } catch (Exception e) {
            return save.getFloat(key);
        }
    }

    public String getString(String key) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getString(key);
        } catch (Exception e) {
            return save.getString(key);
        }
    }

    public boolean getBoolean(String key, boolean defValue) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getBoolean(key);
        } catch (Exception e) {
            return save.getBoolean(key, defValue);
        }
    }

    public int getInteger(String key, int defValue) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getInt(key);
        } catch (Exception e) {
            return save.getInteger(key, defValue);
        }
    }

    public long getLong(String key, long defValue) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getLong(key);
        } catch (Exception e) {
            return save.getLong(key, defValue);
        }
    }

    public float getFloat(String key, float defValue) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getFloat(key);
        } catch (Exception e) {
            return save.getFloat(key, defValue);
        }
    }

    public String getString(String key, String defValue) {
        try {
            DesUtils desUtils = getKeyStore(key);
            String dec = desUtils.decrypt(save.getString(key));
            JSONObject object = new JSONObject(dec);
            return object.getString(key);
        } catch (Exception e) {
            return save.getString(key, defValue);
        }
    }


    public Map<String, ?> get() {
        return save.get();
    }

    public boolean contains(String key) {
        return save.contains(key);
    }

    public void clear() {
        save.clear();
    }

    public void remove(String key) {
        save.remove(key);
    }

    public void flush() {
        save.flush();
    }
}
