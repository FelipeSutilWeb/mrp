/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.monitor;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author raulolivares
 */
@ManagedBean(name = "monitorbean")
@ViewScoped
public class monitor {
    private String MATNR;
    private String PLANTA;
    private String DESCR;
    private String ABC;
    private String XYZ;
    private String ABC_VALUE;
    private String XYZ_AMOUNT;
    private String AVG;
    private String RANGO;
    private String COMENT;
    private String MTyp;
    private String VALSTCKVAL;
    private String CRCY;
    private String VALSTKVALOR;
    private String PESO;
    private String STOCK_VALORADO;
    private String STV_DATO;
    private String CANTIDAD_SC;
    private String CSC_DATO;
    private String UND_CONSUMIDAS;
    private String UNC_DATO;
    private String CDT_ANULAR;
    private String GRADOROTSTKTOT;
    private String CTD_CONS_TOTAL;
    private String NUM_ENTRADAS_SV;
    private String NUM_MOV_MAT;
    private String NUM_SAL_SV;
    private String NUM_SAL_SC;
    private String ULTIMA_ENTRADA;
    private String ULTIMA_SALIDA;
    private String ULTIMO_CONSUMO;
    private String ULTIMO_MOVIM;

    Gson gs = new Gson();
    
    public void getMatrix() throws Exception {
        conexion conexiones = new conexion();
        String val = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("jsonParam");
        
        String sql = "SELECT * FROM tbl_matrix";
        
        try{
            ResultSet rs = conexiones.getSql(sql);
            
            ArrayList ls = new ArrayList();
            
            while (rs.next()){
                monitor mon = new monitor();
                mon.setMATNR(rs.getString("MATNR"));
                mon.setPLANTA(rs.getString("PLANTA"));
                mon.setDESCR(rs.getString("DESCR"));
                mon.setABC(rs.getString("ABC"));
                mon.setXYZ(rs.getString("XYZ"));
                mon.setABC_VALUE(rs.getString("ABC_VALUE"));
                mon.setXYZ_AMOUNT(rs.getString("XYZ_AMOUNT"));
                mon.setAVG(rs.getString("AVG_CONS"));
                mon.setRANGO(rs.getString("RANGO"));
                mon.setCOMENT(rs.getString("COMENTARIO"));
                mon.setMTyp(rs.getString("MTyp"));
                mon.setVALSTCKVAL(rs.getString("VALSTCKVAL"));
                mon.setCRCY(rs.getString("CRCY"));
                ls.add(mon);              
            }
            
            String arr = gs.toJson(ls);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("setGetMatrix("+ arr +")");
        }catch (Exception e){
            throw e;
        }
    }
    
    //Carga/Subuda de Datos del Excel
    public void upDataExcel() throws Exception{
        String error = "Exito";
        String val = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("jsonParam");
        String params[] = val.split("%");
        
        conexion conexiones = new conexion();
               
        String sql = "INSERT INTO tbl_base(MATERIAL, VALSTKVALOR, PESO, STOCK_VALORADO, STV_DATO, CANTIDAD_SC, CSC_DATO, UND_CONSUMIDAS, UNC_DATO, CDT_ANULAR, GRADOROTSTKTOT, CTD_CONS_TOTAL, NUM_ENTRADAS_SV, NUM_MOV_MAT, NUM_SAL_SV, NUM_SAL_SC, ULTIMA_ENTRADA, ULTIMA_SALIDA, ULTIMO_CONSUMO, ULTIMO_MOVIM)" +
                     "VALUES ('" + params[0] + "', '" + params[1] + "', '" + params[2] + "', '" + params[3] + "', '" + params[4] + "', " 
                     + params[5] + ", '" + params[6] + "', '" + params[7] + "', '" + params[8] + "', '" + params[9] + "', '" + params[10] + "', '" 
                     + params[11] + "', '" + params[12] + "', '" + params[13] + "', '" + params[14] + "', '" + params[15] + "', '" + params[16] + "', '" 
                     + params[17] + "', '" + params[18] + "', '" + params[19] + "')";
     
        try{
            conexiones.putSql(sql);
            conexiones.disconnect();
            
        }catch (Exception e) {
            error = e.getMessage();
        }
        
        String arr = gs.toJson(error);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("setUpDataExcel("+ arr +")");
        
    }
    
    public void getDataExcel() throws Exception{
       conexion conexiones = new conexion();
        String val = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("jsonParam");
        
                String sql = "SELECT MATERIAL, CTD_CONS_TOTAL FROM tbl_base ORDER BY CTD_CONS_TOTAL DESC";
        
        try{
            ResultSet rs = conexiones.getSql(sql);
            
            ArrayList ls = new ArrayList();
            
            while (rs.next()){
                monitor mon = new monitor();
                mon.setMATNR(rs.getString("MATERIAL"));
                mon.setCTD_CONS_TOTAL(rs.getString("CTD_CONS_TOTAL"));
                ls.add(mon);              
            }
            
            String arr = gs.toJson(ls);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("table("+ arr +")");
        }catch (Exception e){
            throw e;
        } 
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public String getPLANTA() {
        return PLANTA;
    }

    public void setPLANTA(String PLANTA) {
        this.PLANTA = PLANTA;
    }

    public String getDESCR() {
        return DESCR;
    }

    public void setDESCR(String DESCR) {
        this.DESCR = DESCR;
    }

    public String getABC() {
        return ABC;
    }

    public void setABC(String ABC) {
        this.ABC = ABC;
    }

    public String getXYZ() {
        return XYZ;
    }

    public void setXYZ(String XYZ) {
        this.XYZ = XYZ;
    }

    public String getABC_VALUE() {
        return ABC_VALUE;
    }

    public void setABC_VALUE(String ABC_VALUE) {
        this.ABC_VALUE = ABC_VALUE;
    }

    public String getXYZ_AMOUNT() {
        return XYZ_AMOUNT;
    }

    public void setXYZ_AMOUNT(String XYZ_AMOUNT) {
        this.XYZ_AMOUNT = XYZ_AMOUNT;
    }

    public String getAVG() {
        return AVG;
    }

    public void setAVG(String AVG) {
        this.AVG = AVG;
    }

    public String getRANGO() {
        return RANGO;
    }

    public void setRANGO(String RANGO) {
        this.RANGO = RANGO;
    }

    public String getCOMENT() {
        return COMENT;
    }

    public void setCOMENT(String COMENT) {
        this.COMENT = COMENT;
    }

    public String getMTyp() {
        return MTyp;
    }

    public void setMTyp(String MTyp) {
        this.MTyp = MTyp;
    }
    
    public String getVALSTCKVAL() {
        return VALSTCKVAL;
    }

    public void setVALSTCKVAL(String VALSTCKVAL) {
        this.VALSTCKVAL = VALSTCKVAL;
    }

    public String getCRCY() {
        return CRCY;
    }

    public void setCRCY(String CRCY) {
        this.CRCY = CRCY;
    }
    
        public String getVALSTKVALOR() {
        return VALSTKVALOR;
    }

    public void setVALSTKVALOR(String VALSTKVALOR) {
        this.VALSTKVALOR = VALSTKVALOR;
    }

    public String getPESO() {
        return PESO;
    }

    public void setPESO(String PESO) {
        this.PESO = PESO;
    }

    public String getSTOCK_VALORADO() {
        return STOCK_VALORADO;
    }

    public void setSTOCK_VALORADO(String STOCK_VALORADO) {
        this.STOCK_VALORADO = STOCK_VALORADO;
    }

    public String getSTV_DATO() {
        return STV_DATO;
    }

    public void setSTV_DATO(String STV_DATO) {
        this.STV_DATO = STV_DATO;
    }

    public String getCANTIDAD_SC() {
        return CANTIDAD_SC;
    }

    public void setCANTIDAD_SC(String CANTIDAD_SC) {
        this.CANTIDAD_SC = CANTIDAD_SC;
    }

    public String getCSC_DATO() {
        return CSC_DATO;
    }

    public void setCSC_DATO(String CSC_DATO) {
        this.CSC_DATO = CSC_DATO;
    }

    public String getUND_CONSUMIDAS() {
        return UND_CONSUMIDAS;
    }

    public void setUND_CONSUMIDAS(String UND_CONSUMIDAS) {
        this.UND_CONSUMIDAS = UND_CONSUMIDAS;
    }

    public String getUNC_DATO() {
        return UNC_DATO;
    }

    public void setUNC_DATO(String UNC_DATO) {
        this.UNC_DATO = UNC_DATO;
    }

    public String getCDT_ANULAR() {
        return CDT_ANULAR;
    }

    public void setCDT_ANULAR(String CDT_ANULAR) {
        this.CDT_ANULAR = CDT_ANULAR;
    }

    public String getGRADOROTSTKTOT() {
        return GRADOROTSTKTOT;
    }

    public void setGRADOROTSTKTOT(String GRADOROTSTKTOT) {
        this.GRADOROTSTKTOT = GRADOROTSTKTOT;
    }

    public String getCTD_CONS_TOTAL() {
        return CTD_CONS_TOTAL;
    }

    public void setCTD_CONS_TOTAL(String CTD_CONS_TOTAL) {
        this.CTD_CONS_TOTAL = CTD_CONS_TOTAL;
    }

    public String getNUM_ENTRADAS_SV() {
        return NUM_ENTRADAS_SV;
    }

    public void setNUM_ENTRADAS_SV(String NUM_ENTRADAS_SV) {
        this.NUM_ENTRADAS_SV = NUM_ENTRADAS_SV;
    }

    public String getNUM_MOV_MAT() {
        return NUM_MOV_MAT;
    }

    public void setNUM_MOV_MAT(String NUM_MOV_MAT) {
        this.NUM_MOV_MAT = NUM_MOV_MAT;
    }

    public String getNUM_SAL_SV() {
        return NUM_SAL_SV;
    }

    public void setNUM_SAL_SV(String NUM_SAL_SV) {
        this.NUM_SAL_SV = NUM_SAL_SV;
    }

    public String getNUM_SAL_SC() {
        return NUM_SAL_SC;
    }

    public void setNUM_SAL_SC(String NUM_SAL_SC) {
        this.NUM_SAL_SC = NUM_SAL_SC;
    }

    public String getULTIMA_ENTRADA() {
        return ULTIMA_ENTRADA;
    }

    public void setULTIMA_ENTRADA(String ULTIMA_ENTRADA) {
        this.ULTIMA_ENTRADA = ULTIMA_ENTRADA;
    }

    public String getULTIMA_SALIDA() {
        return ULTIMA_SALIDA;
    }

    public void setULTIMA_SALIDA(String ULTIMA_SALIDA) {
        this.ULTIMA_SALIDA = ULTIMA_SALIDA;
    }

    public String getULTIMO_CONSUMO() {
        return ULTIMO_CONSUMO;
    }

    public void setULTIMO_CONSUMO(String ULTIMO_CONSUMO) {
        this.ULTIMO_CONSUMO = ULTIMO_CONSUMO;
    }

    public String getULTIMO_MOVIM() {
        return ULTIMO_MOVIM;
    }

    public void setULTIMO_MOVIM(String ULTIMO_MOVIM) {
        this.ULTIMO_MOVIM = ULTIMO_MOVIM;
    }
    
}
