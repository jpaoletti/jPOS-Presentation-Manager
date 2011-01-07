package org.jpos.ee.pm.struts.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionMessages;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMMessage;
import org.jpos.ee.pm.core.monitor.Monitor;
import org.jpos.ee.pm.core.monitor.MonitorObserver;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMForwardException;
import org.jpos.ee.pm.struts.PMStrutsContext;
import org.jpos.ee.pm.struts.StrutsMonitorObserver;

public class MonitorAction extends ActionSupport {

    private static final String PM_MONITOR_WATCHER = "pm.monitor.watcher";

    @Override
    protected boolean checkUser() {
        return false;
    }

    @Override
    protected boolean prepare(PMStrutsContext ctx) throws PMException {
        synchronized (ctx.getSession()) {
            super.prepare(ctx);
            String c = (String) ctx.getParameter("continue");
            boolean kontinue = (c != null) && (c.compareTo("true") == 0);
            ctx.put(PM_MONITOR_CONTINUE, kontinue);

            String pmid = ctx.getRequest().getParameter(PMEntitySupport.PM_ID);
            if (pmid == null) {
                pmid = (String) ctx.getSession().getAttribute(PMEntitySupport.LAST_PM_ID);
            } else {
                ctx.getSession().setAttribute(PMEntitySupport.LAST_PM_ID, pmid);
            }
            Monitor monitor = ctx.getPresentationManager().getMonitor(pmid);
            if (monitor == null) {
                ctx.getErrors().add(new PMMessage(ActionMessages.GLOBAL_MESSAGE, "pm.struts.error.monitor.not.found", pmid));
                throw new PMException();
            }
            ctx.put(PM_MONITOR, monitor);
            return true;
        }
    }

    protected void doExecute(PMStrutsContext ctx) throws PMException {
        synchronized (ctx.getSession()) {
            boolean kontinue = (Boolean) ctx.get(PM_MONITOR_CONTINUE);
            Monitor monitor = (Monitor) ctx.get(PM_MONITOR);
            MonitorObserver watcher = (MonitorObserver) ctx.getSession().getAttribute(PM_MONITOR_WATCHER);
            List<String> lines = new ArrayList<String>();
            if (!kontinue || watcher == null) {
                watcher = new StrutsMonitorObserver(monitor);
                ctx.getSession().setAttribute(PM_MONITOR_WATCHER, watcher);
            } else {
                try {
                    lines.addAll(watcher.getLines());
                } catch (Exception e) {
                    ctx.getPresentationManager().error(e);
                    lines.add("ERROR C");
                }
            }
            ctx.getSession().setAttribute("pm_monitor_lines", lines);
            ctx.getSession().setAttribute("monitor", monitor);
            if (kontinue) {
                throw new PMForwardException("monitor_cont");
            }
        }
    }
}
