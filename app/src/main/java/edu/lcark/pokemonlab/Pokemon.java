package edu.lcark.pokemonlab;

/**
 * Created by Greg on 2/18/2016.
 */

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pokemon extends JSONObject implements Parcelable  {

    private String mName, mId, mSpeciesId, mHeight, mWeight, mBXP, mHP, mAttack, mDefence, mSAttack, mSDefence, mSpeed;

    private int id;

    public Pokemon(String csvStr) {
        String[] split = csvStr.trim().split(",");
        mId = split[0];
        mName = split[1];
        mHeight = split[3];
        double height = Double.parseDouble(mHeight)/10;
        mHeight = height + " m";
        mWeight = split[4];
        double weight = Double.parseDouble(mWeight)/10;
        mWeight = weight + " kg";
        id = Integer.parseInt(mId);
    }

    public void setStats(JSONObject jsonObject) {
        try {
            mBXP = jsonObject.getString("base_experience");
            JSONArray jsonArray = jsonObject.getJSONArray("stats");
            mSpeed = jsonArray.getJSONObject(0).getString("base_stat");
            mSDefence = jsonArray.getJSONObject(1).getString("base_stat");
            mSAttack = jsonArray.getJSONObject(2).getString("base_stat");
            mDefence = jsonArray.getJSONObject(3).getString("base_stat");
            mAttack = jsonArray.getJSONObject(4).getString("base_stat");
            mHP = jsonArray.getJSONObject(5).getString("base_stat");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getImageUrl() {
        return "http://img.pokemondb.net/artwork/" + getName() + ".jpg";
    }

    public String getUrl() { return "http://pokeapi.co/api/v2/pokemon/" + getId() +"/";}

    public String getName() { return mName; }

    public String getId() {
        return mId;
    }

    public String getBXP() {
        return mBXP;
    }

    public String getHeight() {
        return mHeight;
    }

    public String getWeight() {
        return mWeight;
    }

    public String getHP() { return mHP; }

    public String getAttack() { return mAttack; }

    public String getDefence() { return mDefence; }

    public String getSAttack() { return mSAttack; }

    public String getSDefence() { return mSDefence; }

    public String getSpeed() { return mSpeed; }

    public int getIDNumber() { return id; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mId);
        dest.writeString(mSpeciesId);
        dest.writeString(mHeight);
        dest.writeString(mWeight);
        dest.writeString(mHP);
        dest.writeString(mAttack);
        dest.writeString(mDefence);
        dest.writeString(mSAttack);
        dest.writeString(mSDefence);
        dest.writeString(mSpeed);
        dest.writeString(mBXP);
        dest.writeInt(id);
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public Pokemon(Parcel source) {
        mName = source.readString();
        mId = source.readString();
        mSpeciesId = source.readString();
        mHeight = source.readString();
        mWeight = source.readString();
        mHP = source.readString();
        mAttack = source.readString();
        mDefence = source.readString();
        mSAttack = source.readString();
        mSDefence = source.readString();
        mSpeed = source.readString();
        mBXP = source.readString();
        id = source.readInt();
    }
}

