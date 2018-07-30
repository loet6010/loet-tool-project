package com.loet.mine.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description BeanClassTool
 * @author liurh
 * @date 2018年7月21日
 */
public class BeanClassTool {
    public static final String SPLIT_REGEX = "_";
    public static final String CHANG_LINE = "\n";

    public static void main(String[] args) {
        List<PropertyModel> list = new ArrayList<>();

        // 设置表字段名称，属性、注释
        list.add(PropertyModel.get("id", "long", "主键ID"));
        list.add(PropertyModel.get("start_ip_num", "long", "起始IP十进制数"));
        list.add(PropertyModel.get("end_ip_num", "long", "结束IP十进制数"));
        list.add(PropertyModel.get("province", "String", "省份"));
        list.add(PropertyModel.get("city", "String", "地级市"));
        list.add(PropertyModel.get("city_sub", "String", "县级市"));
        list.add(PropertyModel.get("isp", "String", "运营商"));
        list.add(PropertyModel.get("start_ip", "String", "起始IP地址"));
        list.add(PropertyModel.get("end_ip", "String", "结束IP地址"));
        list.add(PropertyModel.get("ipDvalue", "int", "IP差值"));

        getBeanClass(list);
    }

    /**
     * 获取属性bean内容
     *
     * @param list
     */
    public static void getBeanClass(List<PropertyModel> list) {
        StringBuilder propertyBuilder = new StringBuilder();
        StringBuilder methodBuilder = new StringBuilder();
        for (PropertyModel propertyModel : list) {
            String name = getPropertyName(propertyModel.getName());
            String getName = getPropertyGetName(name);
            String setName = getPropertySetName(name);

            propertyBuilder.append("    // ").append(propertyModel.getComment()).append(CHANG_LINE);
            propertyBuilder.append("    private ").append(propertyModel.getType()).append(" ").append(name).append(";")
                    .append(CHANG_LINE);

            methodBuilder.append(CHANG_LINE).append("/**").append(CHANG_LINE).append("*获取")
                    .append(propertyModel.getComment()).append(CHANG_LINE).append("* @return the ").append(name)
                    .append(CHANG_LINE).append("*/").append(CHANG_LINE).append("public ")
                    .append(propertyModel.getType()).append(" ").append(getName).append("() {").append(CHANG_LINE)
                    .append("return ").append(name).append(";").append(CHANG_LINE).append("}").append(CHANG_LINE);

            methodBuilder.append(CHANG_LINE).append("/**").append(CHANG_LINE).append("*设置")
                    .append(propertyModel.getComment()).append(CHANG_LINE).append("* @param ").append(name)
                    .append(CHANG_LINE).append("*/").append(CHANG_LINE).append("public void ").append(setName)
                    .append("(").append(propertyModel.getType()).append(" ").append(name).append(") {")
                    .append(CHANG_LINE).append("this.").append(name).append(" = ").append(name).append(";")
                    .append(CHANG_LINE).append("}");
        }

        propertyBuilder.append(methodBuilder.toString());

        System.out.println(propertyBuilder.toString());
    }

    /**
     * 获取驼峰形式的属性名
     *
     * @param name
     * @return
     */
    public static String getPropertyName(String name) {
        if (name.indexOf(SPLIT_REGEX) < 0) {
            return name;
        } else {
            StringBuilder nameBuilder = new StringBuilder();
            String[] splitNames = name.split(SPLIT_REGEX);
            nameBuilder.append(splitNames[0]);
            for (int i = 1; i < splitNames.length; i++) {
                String subName = splitNames[i];
                subName = subName.replaceFirst(subName.substring(0, 1), subName.substring(0, 1).toUpperCase());
                nameBuilder.append(subName);
            }

            return nameBuilder.toString();
        }
    }

    /**
     * 获取get方法名
     *
     * @param name
     * @return
     */
    public static String getPropertyGetName(String name) {
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append("get");
        nameBuilder.append(name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase()));

        return nameBuilder.toString();
    }

    /**
     * 获取set方法名
     *
     * @param name
     * @return
     */
    public static String getPropertySetName(String name) {
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append("set");
        nameBuilder.append(name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase()));

        return nameBuilder.toString();
    }

    /**
     * 属性模型
     * 
     * @Description PropertyModel
     * @author liurh
     * @date 2018年7月27日
     */
    private static class PropertyModel {
        private String name;
        private String type;
        private String comment;

        public static PropertyModel get(String name, String type, String comment) {
            return new PropertyModel(name, type, comment);
        }

        public PropertyModel(String name, String type, String comment) {
            this.name = name;
            this.type = type;
            this.comment = comment;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getComment() {
            return comment;
        }
    }
}
