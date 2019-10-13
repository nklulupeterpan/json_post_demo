package com.example.lu.demoJson.service;

import com.example.lu.demoJson.model.Attendee;
import com.example.lu.demoJson.model.Attendee2;
import com.example.lu.demoJson.model.Countries;
import com.example.lu.demoJson.model.Partner;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalculateAttend {

    HashMap<String, Attendee> list = new HashMap<>();

    public Countries doCalculate(List<Partner> partnerList) {
        for (Partner partner : partnerList) {

            if (list.get(partner.getCountry()) == null) {
                Attendee attendee = new Attendee();
                attendee.setName(partner.getCountry());
                attendee.setStartDate(getStartDayRow(partner.getAvailableDates(), new ArrayList<>()));
            }else{
                Attendee attendee = list.get(partner.getCountry());
                attendee.setStartDate( getStartDayRow(partner.getAvailableDates(), attendee.getStartDate()));
            }
        }

        HashMap<String, Attendee2> list2 = new HashMap<>();
        for (Map.Entry<String, Attendee> entry : list.entrySet()) {
            String key = entry.getKey();
            Attendee attendee = entry.getValue();
            Attendee2 attendee2 = new Attendee2();
            attendee2.setAttendeeCount(0);
            attendee2.setName(key);
            attendee2.setStartDate(mostCommonElement(attendee.getStartDate()));
            list2.put(key, attendee2);
        }


        for (Partner partner : partnerList) {

        Attendee2 attendee2 = list2.get(partner.getCountry());
            if(partner.getAvailableDates().contains(attendee2.getStartDate())){
                int count = attendee2.getAttendeeCount() +1;
                attendee2.getAttendees().add(partner.getEmail());
                attendee2.setAttendeeCount(count+1);
            }
        }

        Countries countries = new Countries();
        countries.setAttendees(new ArrayList(list2.values()));
        return countries;
    }


    private static Date mostCommonElement(List<Date> list) {

        Map<Date, Integer> map = new HashMap<Date, Integer>();

        for(int i=0; i< list.size(); i++) {

            Integer frequency = map.get(list.get(i));
            if(frequency == null) {
                map.put(list.get(i), 1);
            } else {
                map.put(list.get(i), frequency+1);
            }
        }

        Date mostCommonKey = null;
        int maxValue = -1;
        for(Map.Entry<Date, Integer> entry: map.entrySet()) {

            if(entry.getValue() > maxValue) {
                mostCommonKey = entry.getKey();
                maxValue = entry.getValue();
            }
        }

        return mostCommonKey;
    }

    private List<Date> getStartDayRow(List<Date> availableDays, List<Date> startDate) {
        for (int i = 0; i < availableDays.size()-1; i++) {
            Date next = availableDays.get(i + 1);
            Date current = availableDays.get(i);
            Calendar cal = Calendar.getInstance();
            cal.setTime(current);
            cal.add(Calendar.DATE, 1);
            if (cal.getTime().compareTo(next) == 0) {
                startDate.add(current);
            }
        }
      return startDate;
    }
}
