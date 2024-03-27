package com.mashibing.internalcommon.utils;


/**
 * @author: kavanLi
 * @create: 2019-08-01 15:53
 * To change this template use File | Settings | File and Code Templates.
 */
public final class CriteriaUtils {

    /* fields -------------------------------------------------------------- */


    private CriteriaUtils() {
    }

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */

    //public static Criteria criteria4reverse(Criteria criteria, String operator, Boolean reverseStatus,
    //                               String field,
    //                               String... fieldValue) {
    //    if (StringUtils.equalsIgnoreCase(operator, "is")) {
    //        if (reverseStatus) {
    //            // 逆向搜索
    //            criteria.and(field).ne(fieldValue[0]);
    //        } else {
    //            criteria.and(field).is(fieldValue[0]);
    //        }
    //    }
    //
    //    if (StringUtils.equalsIgnoreCase(operator, "between")) {
    //        if (reverseStatus) {
    //            // 逆向搜索
    //            criteria.and(field).lt(Integer.valueOf(fieldValue[0])).gt(Integer.valueOf(fieldValue[1]));
    //        } else {
    //            criteria.and(field).gte(Integer.valueOf(fieldValue[0])).lte(Integer.valueOf(fieldValue[1]));
    //        }
    //    }
    //
    //    if (StringUtils.equalsIgnoreCase(operator, "regex")) {
    //        if (reverseStatus) {
    //            // 逆向搜索
    //            // A regex has no $not-like operators.
    //            // this regex result is not contain a query string
    //            // { $regex: '^(?:(?![your_query_string]).)*$' }
    //            String regexValue = "^(?:(?!" + com.example.demo.utils.utils.RegexUtils.makeQueryStringToRegExp(fieldValue[0]) + ").)*$";
    //            criteria.and(field).regex(regexValue);
    //        } else {
    //            criteria.and(field).regex(RegexUtils.makeQueryStringToRegExp(fieldValue[0]));
    //        }
    //    }
    //
    //    return criteria;
    //}


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
