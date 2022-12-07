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
        User user = new User();
        user.setAccessId(accessId);
        user.setNickName(nickName);
        user.setLevel(level);

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

    public static ArrayList<History> getMarketHistoryFromJsonString(String json) {
        ArrayList<History> purchases = new ArrayList<>(0);

        try {
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i < jsonArray.length(); i++) {
                History history = new History();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String tradeDate = jsonObject.getString("tradeDate");
                String[] part = tradeDate.split("T");
                history.setTradeDate(part[0] + " / " + part[1]);
                history.setSpid(jsonObject.getInt("spid"));
                history.setValue(jsonObject.getLong("value"));
                history.setGrade(jsonObject.getInt("grade"));
                purchases.add(history);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return purchases;
    }

}
