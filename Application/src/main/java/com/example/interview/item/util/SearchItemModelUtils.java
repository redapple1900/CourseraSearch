package com.example.interview.item.util;

import android.util.SparseArray;

import com.example.interview.model.SearchResult;
import com.example.interview.model.elements.SearchElement;
import com.example.interview.model.elements.Entry;
import com.example.interview.model.elements.LinkedResource.Course;
import com.example.interview.model.elements.LinkedResource.Partner;
import com.example.interview.model.elements.LinkedResource.Specialization;
import com.example.interview.model.elements.PageInfo;
import com.example.interview.item.SearchItemModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchItemModelUtils {

  public static void updatePageInfo(SearchResult result, PageInfo pageInfo) {
    if (result == null || result.getPageInfo() == null) return;

    pageInfo.setNext(result.getPageInfo().getNext()).setTotal(result.getPageInfo().getTotal());
  }

  public static void updateDataSet(
      SearchResult result,
      List<SearchItemModel> dataSet) {

    if (result == null ||
        result.getSearchElement() == null ||
        result.getSearchElement().isEmpty() ||
        result.getLinkedResource() == null ||
        result.getPageInfo() == null) {
      return;
    }

    SparseArray<String> univMap = new SparseArray<>();
    Map<String, Course> courseMap = new HashMap<>();
    Map<String, Specialization> specializationMap = new HashMap<>();

    for (Partner partner : result.getLinkedResource().getPartnerList()) {
      univMap.put(partner.getId(), partner.getName());
    }

    for (Course course : result.getLinkedResource().getCourseList()) {
      courseMap.put(course.getId(), course);
    }

    for (Specialization specialization : result.getLinkedResource().getSpecializationList()) {
      specializationMap.put(specialization.getId(), specialization);
    }

    List<SearchItemModel> models = new ArrayList<>();

    for (SearchElement searchElement : result.getSearchElement()) {
      for (Entry entry : searchElement.getEntryList()) {
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

  private static List<String> getPartnerNameList(List<Integer> list, SparseArray<String> array) {
    List<String> univNameList = new ArrayList<>();
    for (Integer partnerId : list) {
      if (array.get(partnerId) == null) continue;
      univNameList.add(array.get(partnerId));
    }
    return univNameList;
  }
}
