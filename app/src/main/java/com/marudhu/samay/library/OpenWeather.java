package com.marudhu.samay.library;

import java.util.Map;

/**
 * Created by marudhu on 19/8/14.
 */
public class OpenWeather {

    private String imageurl ="http://openweathermap.org/img/w/"

    private Map<Integer , String>  map;

    public OpenWeather(){
        map.put(200,imageurl + "11d.png");
        map.put(201,imageurl + "11d.png");
        map.put(202,imageurl + "11d.png");
        map.put(210,imageurl + "11d.png");
        map.put(211,imageurl + "11d.png");
        map.put(212,imageurl + "11d.png");
        map.put(221,imageurl + "11d.png");
        map.put(230,imageurl + "11d.png");
        map.put(231,imageurl + "11d.png");
        map.put(232,imageurl + "11d.png");


        map.put(300,imageurl + "09d.png");
        map.put(301,imageurl + "09d.png");
        map.put(302,imageurl + "09d.png");
        map.put(310,imageurl + "09d.png");
        map.put(311,imageurl + "09d.png");
        map.put(302,imageurl + "09d.png");
        map.put(313,imageurl + "09d.png");
        map.put(314,imageurl + "09d.png");
        map.put(321,imageurl + "09d.png");

        map.put(500,imageurl + "10d.png");
        map.put(501,imageurl + "10d.png");
        map.put(502,imageurl + "10d.png");
        map.put(503,imageurl + "10d.png");
        map.put(504,imageurl + "10d.png");
        map.put(511,imageurl + "10d.png");
        map.put(520,imageurl + "10d.png");
        map.put(522,imageurl + "10d.png");
        map.put(531,imageurl + "10d.png");


        map.put(600,imageurl + "13d.png");
        map.put(601,imageurl + "13d.png");
        map.put(602,imageurl + "13d.png");
        map.put(611,imageurl + "13d.png");
        map.put(612,imageurl + "13d.png");
        map.put(615,imageurl + "13d.png");
        map.put(616,imageurl + "13d.png");
        map.put(620,imageurl + "13d.png");
        map.put(621,imageurl + "13d.png");
        map.put(622,imageurl + "13d.png");

        map.put(701,imageurl + "50d.png");
        map.put(711,imageurl + "50d.png");
        map.put(721,imageurl + "50d.png");
        map.put(731,imageurl + "50d.png");
        map.put(741,imageurl + "50d.png");
        map.put(751,imageurl + "50d.png");
        map.put(761,imageurl + "50d.png");
        map.put(761,imageurl + "50d.png");
        map.put(771,imageurl + "50d.png");
        map.put(781,imageurl + "50d.png");

        map.put(800,imageurl + "01n.png");
        map.put(801,imageurl + "02n.png");
        map.put(802,imageurl + "03n.png");
        map.put(803,imageurl + "03n.png");
        map.put(804,imageurl + "04n.png");

    }

    public String getImageUrl(int condCode){

        try{
            return map.get(condCode);
        }catch (Exception e){
            return imageurl + "10d.png";
        }
    }
}
