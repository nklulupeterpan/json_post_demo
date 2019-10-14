package com.example.lu.demoJson.service;

import com.example.lu.demoJson.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CalculateAttend {
    private static final Logger logger = LoggerFactory.getLogger(CalculateAttend.class);

    HashMap<String, StartDatesPerCountry> list = new HashMap<>();

    public Countries doCalculate(List<Partner> partnerList) {
        //store all qualified start days for all partners per country
        for (Partner partner : partnerList) {

            if (list.get(partner.getCountry()) == null) {
                StartDatesPerCountry startDatesPerCountry = new StartDatesPerCountry();
                startDatesPerCountry.setName(partner.getCountry());
                startDatesPerCountry.setStartDates(getStartDayRow(partner.getAvailableDates(), new ArrayList<>()));
                list.put(partner.getCountry(), startDatesPerCountry);
            } else {
                StartDatesPerCountry startDatesPerCountry = list.get(partner.getCountry());
                startDatesPerCountry.setStartDates(getStartDayRow(partner.getAvailableDates(), startDatesPerCountry.getStartDates()));
                list.put(partner.getCountry(), startDatesPerCountry);
            }
        }

        //calculate the most frequent start date per country
        HashMap<String, Attendee> attendeeList = new HashMap<>();
        for (Map.Entry<String, StartDatesPerCountry> entry : list.entrySet()) {
            String key = entry.getKey();
            StartDatesPerCountry startDatesPerCountry = entry.getValue();
            Attendee attendee = new Attendee();
            attendee.setAttendeeCount(0);
            attendee.setName(key);
            if (startDatesPerCountry.getStartDates() == null || startDatesPerCountry.getStartDates().isEmpty()) {
                attendee.setStartDate(null);
            } else {
                attendee.setStartDate(mostCommonElement(startDatesPerCountry.getStartDates()));
            }
            attendeeList.put(key, attendee);
        }


        // find and count the attendees that has the most frequent start date
        for (Partner partner : partnerList) {

            Attendee attendee = attendeeList.get(partner.getCountry());
            if (partner.getAvailableDates().contains(attendee.getStartDate())) {
                int count = attendee.getAttendeeCount() + 1;
                attendee.getAttendees().add(partner.getEmail());
                attendee.setAttendeeCount(count);
            }
        }

        Countries countries = new Countries();
        countries.setCountries(new ArrayList(attendeeList.values()));
        return countries;
    }


    private static String mostCommonElement(List<String> list) {

        Collections.sort(list);
        Map<String, Integer> map = new TreeMap<>();

        for (int i = 0; i < list.size(); i++) {

            Integer frequency = map.get(list.get(i));
            if (frequency == null) {
                map.put(list.get(i), 1);
            } else {
                map.put(list.get(i), frequency + 1);
            }
        }

        String mostCommonKey = null;
        int maxValue = -1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            if (entry.getValue() > maxValue) {
                mostCommonKey = entry.getKey();
                maxValue = entry.getValue();
            }
        }
        logger.info("find most frequent date", mostCommonKey);

        return mostCommonKey;
    }

    private List<String> getStartDayRow(List<String> availableDays, List<String> startDate) {
        for (int i = 0; i < availableDays.size() - 1; i++) {
            String next = availableDays.get(i + 1);
            String current = availableDays.get(i);
            String nextDay = LocalDate.parse(current).plusDays(1).toString();
            if (next.equalsIgnoreCase(nextDay)) {
                startDate.add(current);
            }
        }
        return startDate;
    }
}
