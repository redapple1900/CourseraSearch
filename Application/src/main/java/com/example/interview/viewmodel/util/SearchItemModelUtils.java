package com.example.interview.viewmodel.util;

import com.example.interview.model.CourseSearchResponse;
import com.example.interview.model.elements.Element;
import com.example.interview.model.elements.Entry;
import com.example.interview.model.elements.LinkedResource.Course;
import com.example.interview.model.elements.LinkedResource.Partner;
import com.example.interview.model.elements.LinkedResource.Specialization;
import com.example.interview.model.elements.PageInfo;
import com.example.interview.viewmodel.SearchItemModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchItemModelUtils {

  public static void updatePageInfo(CourseSearchResponse response, PageInfo pageInfo) {
    if (response == null || response.getPageInfo() == null) return;

    pageInfo.setNext(response.getPageInfo().getNext()).setTotal(response.getPageInfo().getTotal());
  }

  public static void updateDataSet(
      CourseSearchResponse response,
      List<SearchItemModel> dataSet) {

    if (response == null ||
        response.getElement() == null ||
        response.getElement().isEmpty() ||
        response.getLinkedResource() == null ||
        response.getPageInfo() == null) {
      return;
    }

    Map<Integer, String> univMap = new HashMap<>();
    Map<String, Course> courseMap = new HashMap<>();
    Map<String, Specialization> specializationMap = new HashMap<>();

    for (Partner partner : response.getLinkedResource().getPartnerList()) {
      univMap.put(partner.getId(), partner.getName());
    }

    for (Course course : response.getLinkedResource().getCourseList()) {
      courseMap.put(course.getId(), course);
    }

    for (Specialization specialization : response.getLinkedResource().getSpecializationList()) {
      specializationMap.put(specialization.getId(), specialization);
    }

    List<SearchItemModel> models = new ArrayList<>();

    for (Element element : response.getElement()) {
      for (Entry entry : element.getEntryList()) {
        if (entry.getType() == Entry.Type.COURSE) {
          if (!courseMap.containsKey(entry.getId())) {
            continue;
          }

          Course course = courseMap.get(entry.getId());
          models.add(
              new SearchItemModel()
                  .setId(course.getId())
                  .setName(course.getName())
                  .setPhotoUrl(course.getPhotoUrl())
                  .setType(SearchItemModel.Type.COURSE)
                  .setCourseAmount(0)
                  .setUnivNameList(getPartnerNameList(course.getPartnerIds(), univMap)));
        } else if (entry.getType() == Entry.Type.SPECIALIZATION) {
          if (!specializationMap.containsKey(entry.getId())) {
            continue;
          }

          Specialization specialization = specializationMap.get(entry.getId());
          models.add(
              new SearchItemModel()
                  .setId(specialization.getId())
                  .setName(specialization.getName())
                  .setPhotoUrl(specialization.getLogo())
                  .setType(SearchItemModel.Type.SPECIALIZATION)
                  .setCourseAmount(specialization.getCourseIds().size())
                  .setUnivNameList(getPartnerNameList(specialization.getPartnerIds(), univMap)));
        }
      }
    }
    dataSet.addAll(models);
  }

  private static List<String> getPartnerNameList(List<Integer> list, Map<Integer, String> map) {
    List<String> univNameList = new ArrayList<>();
    for (Integer partnerId : list) {
      if (!map.containsKey(partnerId)) continue;
      univNameList.add(map.get(partnerId));
    }
    return univNameList;
  }
}
