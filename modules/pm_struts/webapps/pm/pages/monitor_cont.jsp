<%java.util.List<String> list = (java.util.List<String>)session.getAttribute("pm_monitor_lines");
for(String s : list){out.println(s.replaceAll("<","&lt;").replaceAll(">","&gt;"));} %>