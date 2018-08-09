package com.loet.mine.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liurh
 * @Description BeanClassTool
 * @date 2018年7月21日
 */
public class BeanClassTool {
    private static final String SPLIT_REGEX = "_";
    private static final String CHANG_LINE = "\n";

    public static void main(String[] args) {
        List<PropertyModel> list = new ArrayList<>();

        // 设置表字段名称，属性、注释
        list.add(PropertyModel.get("id", "long", "主键ID"));
        list.add(PropertyModel.get("passageway_id", "String", "通道ID"));
        list.add(PropertyModel.get("passageway_name", "String", "通道名称"));
        list.add(PropertyModel.get("province", "String", "省份"));
        list.add(PropertyModel.get("question", "String", "问题"));
        list.add(PropertyModel.get("answer", "String", "答案"));
        list.add(PropertyModel.get("status", "String", "类型"));
        list.add(PropertyModel.get("create_time", "Date", "创建时间"));
        list.add(PropertyModel.get("modify_time", "Date", "修改时间"));
        getBeanClass(list);
    }

    /**
     * 获取属性bean内容
     *
     * @param list
     */
    private static void getBeanClass(List<PropertyModel> list) {
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

        methodBuilder.append(CHANG_LINE).append("/**").append(CHANG_LINE).append("* @return").append(CHANG_LINE)
                .append("*/").append(CHANG_LINE).append("@Override").append(CHANG_LINE)
                .append("public String toString() {").append(CHANG_LINE)
                .append("return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);")
                .append(CHANG_LINE).append("}");

        propertyBuilder.append(methodBuilder.toString());

        System.out.println(propertyBuilder.toString());
    }

    /**
     * 获取驼峰形式的属性名
     *
     * @param name
     * @return
     */
    private static String getPropertyName(String name) {
        if (!name.contains(SPLIT_REGEX)) {
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
    private static String getPropertyGetName(String name) {
        return "get" +
                name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
    }

    /**
     * 获取set方法名
     *
     * @param name
     * @return
     */
    private static String getPropertySetName(String name) {
        return "set" +
                name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
    }

    /**
     * 属性模型
     *
     * @author liurh
     * @Description PropertyModel
     * @date 2018年7月27日
     */
    private static class PropertyModel {
        private String name;
        private String type;
        private String comment;

        static PropertyModel get(String name, String type, String comment) {
            return new PropertyModel(name, type, comment);
        }

        PropertyModel(String name, String type, String comment) {
            this.name = name;
            this.type = type;
            this.comment = comment;
        }

        String getName() {
            return name;
        }

        String getType() {
            return type;
        }

        String getComment() {
            return comment;
        }
    }
}
