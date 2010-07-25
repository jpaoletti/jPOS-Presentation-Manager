package org.jpos.ee.pm.core.monitor;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

/**
 * A formatter that receives an array and returns a formatted string.
 *
 * This formatter has two properties
 *
 * <b>separator</b> an intem separator
 * <b>pads</b> a list of # separated padding item representation. Each padding
 * item representation hast the form [A,B,C,D] where
 * A: Index of the array where the item will be taken
 * B: L or R, indicates the direction of the padding (Left or right)
 * C: Padding character
 * D: Padding length
 *
 * @author jpaoletti
 */
public class BasicObjectArrayFormatter extends MonitorFormatter {
    
    @Override
    public String format(MonitorLine line){
        Object[] objects = (Object[]) line.getValue();
        String[] pads = getConfig("pads", "").split("#");
        //[0,L,0,2] = [column , L or R pad direction , fill char , pad count]
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            String pad = getPad(pads, i);
            if(pad == null){
                sb.append(object.toString());
            }else{
                String[] ss = pad.split(",");
                if(ss.length == 4){
                    boolean left = (ss[1].trim().compareTo("L")==0);
                    char fill = ss[2].charAt(0);
                    int count = 0;
                    try {count = Integer.parseInt(ss[3].trim());} catch (Exception e) {}
                    if(left){
                        try {
                            sb.append(ISOUtil.padleft(object.toString(), count, fill));
                        } catch (ISOException e) {
                            sb.append(object.toString());
                        }
                    }else{
                        try {
                            sb.append(ISOUtil.padright(object.toString(), count, fill));
                        } catch (ISOException e) {
                            sb.append(object.toString());
                        }
                    }
                }else{
                    sb.append(object.toString());
                }
            }
            sb.append(getConfig("separator", " "));
        }
        return sb.toString();
    }

    private String getPad(String[] pads, int i) {
        for (int j = 0; j < pads.length; j++) {
            String pad = pads[j].trim();
            pad = pad.replace('[', ' ').replace(']', ' ').trim();
            String[] ss = pad.split(",");
            if(ss.length == 4){
                try {
                    if(Integer.parseInt(ss[0]) == i)
                        return pad;
                } catch (Exception e) {}
            }
        }
        return null;
    }
}