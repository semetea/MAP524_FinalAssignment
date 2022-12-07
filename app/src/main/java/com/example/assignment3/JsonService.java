package com.example.assignment3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {

    public static User getUserFromJsonOBject(JSONObject json) throws JSONException {
        String accessId = json.getString("accessId");
        String nickName = json.getString("nickname");
        int level = json.getInt("level");
        User user = new User(accessId, nickName, level);

        return user;
    }

    public static Rank getdivisionFromJsonString(String json){
        Rank rank = new Rank();

        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject rankObject = jsonArray.getJSONObject(0);
            rank.setDivision(rankObject.getInt("division"));
            rank.setMatchType(rankObject.getInt("matchType"));
            rank.setAchievementDate(rankObject.getString("achievementDate"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rank;
    }
}
