package com.ittianyu.relight.utils;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

import com.ittianyu.relight.convertor.JsonConvertor;
import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseAndroidWidget;
import com.ittianyu.relight.widget.native_.ViewGroupWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WidgetInflateUtils {
    public static final String CLASS = "class";

    public static Widget createWidget(String widgetName, Context context, Lifecycle lifecycle) {
        try {
            Class<?> clazz = Class.forName(widgetName);
            Constructor<?> constructor = clazz.getConstructor(Context.class, Lifecycle.class);
            return (Widget) constructor.newInstance(context, lifecycle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(widgetName + "\n" + e.getMessage(), e);
        }
    }

    public static void initWidgetWithParams(Context context, Widget widget, String params) {
        JSONObject json = parseParams(params);
        if (json == null) {
            return;
        }
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                Object obj = dealParam(context, json.get(key));
                Method method = null;
                if (obj != null) {
                    try {
                        method = widget.getClass().getMethod(key, obj.getClass());
                    } catch (Exception t) {
                        Class paramClass = null;
                        if (obj instanceof Integer) {
                            paramClass = int.class;
                        } else if (obj instanceof Long) {
                            paramClass = long.class;
                        } else if (obj instanceof Float) {
                            paramClass = float.class;
                        } else if (obj instanceof Double) {
                            paramClass = double.class;
                        } else if (obj instanceof Boolean) {
                            paramClass = boolean.class;
                        }
                        if (paramClass != null) {
                            method = widget.getClass().getMethod(key, paramClass);
                        }
                    }
                    if (null != method) {
                        method.invoke(widget, obj);
                    }
                } else {
                    method = widget.getClass().getMethod(key);
                    method.invoke(widget);
                }
                dealKey(widget, key, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static JSONObject parseParams(String params) {
        if (null == params) {
            return null;
        }
        params = params.replaceAll("'", "\"");
        try {
            return new JSONObject(params);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object dealParam(Context context, Object o) {
        // double to float
        if (o instanceof Double) {
            o = (Float)((Double) o).floatValue();
        }

        // json to bean
        if (o instanceof JSONArray) {
            JSONArray json = ((JSONArray) o);
            o = JsonConvertor.getInstance().fromJson(json.toString(), List.class);
        } else if (o instanceof JSONObject) {
            try {
                JSONObject json = ((JSONObject) o);
                String className = json.getString(CLASS);
                Class<?> clazz = Class.forName(className);
                o = JsonConvertor.getInstance().fromJson(json.toString(), clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // visibility
        String param = o.toString().trim();
        switch (param.toLowerCase()) {
            case "gone":
                o = View.GONE;
                break;
            case "visible":
                o = View.VISIBLE;
                break;
            case "invisible":
                o = View.INVISIBLE;
                break;
        }

        // size
        switch (param.toLowerCase()) {
            case "wrap_content":
                o = ViewGroup.LayoutParams.WRAP_CONTENT;
                break;
            case "match_parent":
                o = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
        }

        // color
        if (param.startsWith("#")) {
            o = Color.parseColor(param);
        }

        // ref
        if (param.contains("R.")) {
            String packageName = null;
            if (param.startsWith("R.")) {
                packageName = context.getPackageName();
            } else if (param.contains(".R.")) {
                int indexR = param.indexOf(".R.");
                packageName = param.substring(0, indexR);
                param = param.substring(indexR + 1);// xxx.R.xxx.xx -> R.xxx.xx
            }
            param = param.substring(2);// R.xxx.xx -> xxx.xx
            int indexPoint = param.indexOf(".");
            String innerClass = param.substring(0, indexPoint);// xxx.xx -> xxx
            try {
                Class<?> clazz = Class.forName(packageName + ".R$" + innerClass);// get xxx.R.xxx
                String filedName = param.substring(indexPoint + 1);// xxx.xx -> xx
                o = clazz.getField(filedName).get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // dp sp
        if (param.endsWith("sp") || param.endsWith("dp")) {
            String value = param.substring(0, param.indexOf("p") - 1);
            o = Float.parseFloat(value);
        }

        // orientation
        switch (param.toLowerCase()) {
            case "horizontal":
                o = LinearLayout.HORIZONTAL;
                break;
            case "vertical":
                o = LinearLayout.VERTICAL;
                break;
        }

        // gravity
        switch (param.toLowerCase()) {
            case "center":
                o = Gravity.CENTER;
                break;
            case "center_horizontal":
                o = Gravity.CENTER_HORIZONTAL;
                break;
            case "center_vertical":
                o = Gravity.CENTER_VERTICAL;
                break;
            case "end":
                o = Gravity.END;
                break;
            case "bottom":
                o = Gravity.BOTTOM;
                break;
            case "clip_horizontal":
                o = Gravity.CLIP_HORIZONTAL;
                break;
            case "clip_vertical":
                o = Gravity.CLIP_VERTICAL;
                break;
            case "fill":
                o = Gravity.FILL;
                break;
            case "fill_horizontal":
                o = Gravity.FILL_HORIZONTAL;
                break;
            case "fill_vertical":
                o = Gravity.FILL_VERTICAL;
                break;
            case "left":
                o = Gravity.LEFT;
                break;
            case "right":
                o = Gravity.RIGHT;
                break;
            case "start":
                o = Gravity.START;
                break;
            case "top":
                o = Gravity.TOP;
                break;
        }

        if (o == JSONObject.NULL) {
            o = null;
        }
        return o;
    }

    private static void dealKey(Widget widget, String key, Object obj) {
        // deal with layoutGravity
        if (key.equals("layoutGravity")) {
            LayoutParams lp = (LayoutParams) widget.render().getLayoutParams();
            lp.gravity = (int) obj;
        }
    }

    public static void updateProps(Widget w) {
        Queue<Widget> widgets = new LinkedList<>();
        widgets.add(w);
        while (!widgets.isEmpty()) {
            Widget widget = widgets.poll();
            if (widget instanceof BaseAndroidWidget) {
                ((BaseAndroidWidget) widget).updateProps(widget.render());
            } else if (widget instanceof ContainerWidget) {
                Widget innerWidget = ((ContainerWidget) widget).getInnerWidget();
                widgets.add(innerWidget);
            }
        }
    }
}
