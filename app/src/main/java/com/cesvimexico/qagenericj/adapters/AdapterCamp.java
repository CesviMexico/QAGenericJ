package com.cesvimexico.qagenericj.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cesvimexico.qagenericj.R;
import com.cesvimexico.qagenericj.db.DBManager;
import com.cesvimexico.qagenericj.model.Campo;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class AdapterCamp extends RecyclerView.Adapter<AdapterCamp.ViewHolder> {

    private String TAG = "EVALUACION";
    private ArrayList<Campo> dataSet;
    private Context context;
    private String idE;
    private String sql = "";
    private DBManager bdm;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int id_evaluacion;

    private ArrayList<String> resultMunicipios = new ArrayList<>();
    private ArrayAdapter<String> adapterMunicipios;
    private MaterialSpinner municipiosSpinner;
    boolean isOnTextChanged = false;

    private Map<String, String> fields = new HashMap<String, String>();

    private FloatingActionButton buttonGuardarServ;


    public AdapterCamp(Context context, ArrayList<Campo> dataset, String idE, int id_evaluacion) {
        this.context = context;
        this.dataSet = dataset;
        this.idE = idE;
        this.id_evaluacion = id_evaluacion;
        this.bdm = new DBManager(context);
        this.db = bdm.getWritableDatabase();
        this.buttonGuardarServ = buttonGuardarServ;
    }

    @NonNull
    @Override
    public AdapterCamp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_form, parent, false);
        return new AdapterCamp.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCamp.ViewHolder holder, int position) {
        isOnTextChanged = false;

        MaterialDatePicker.Builder materialDateBuilder_ini = MaterialDatePicker.Builder.datePicker();
        //CalendarConstraints.Builder calendarConstraintBuilder = new CalendarConstraints.Builder();
        //calendarConstraintBuilder.setValidator(DateValidatorPointForward.now());
        materialDateBuilder_ini.setTitleText("Seleccione la fecha de inicio");
        //materialDateBuilder_ini.setCalendarConstraints(calendarConstraintBuilder.build());
        final MaterialDatePicker materialDatePicker_ini = materialDateBuilder_ini.build();

        //holder.getAdapterPosition()

        holder.editTextFR.setText("");
        holder.editTextFR.setVisibility(View.GONE);
        holder.spinnerFR.setVisibility(View.GONE);
        holder.buttonFR.setVisibility(View.GONE);
        //holder.editTextFR.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        holder.editTextFR.setKeyListener(new EditText(context).getKeyListener());
        holder.txtEtiquetaFR.setText(dataSet.get(holder.getAdapterPosition()).getEtiqueta());


        switch (dataSet.get(holder.getAdapterPosition()).getTipo()) {
            case "DATETIME":
            case "DATE":
                //holder.editTextFR.setInputType(InputType.TYPE_CLASS_DATETIME);
                holder.editTextFR.setKeyListener(null);
                holder.editTextFR.setVisibility(View.VISIBLE);
                holder.buttonFR.setVisibility(View.VISIBLE);


                sql = "SELECT valor_fecha FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(position).getId_campo() + "'";
                cursor = db.rawQuery(sql, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        holder.editTextFR.setText(cursor.getString(0));
                    }
                }

                holder.buttonFR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker_ini.show(((AppCompatActivity) context).getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });
                materialDatePicker_ini.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                TimeZone timeZoneUTC = TimeZone.getDefault();
                                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                                SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                Date date = new Date((Long) selection + offsetFromUTC);
                                holder.editTextFR.setText(simpleFormat.format(date));
                                fields.put(Integer.toString(dataSet.get(holder.getAdapterPosition()).getId_campo()), holder.editTextFR.getText().toString());
                                /*
                                verificar si el campo ya fue almacenado o no insert or update
                                 */

                                sql = "SELECT id_campo FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                cursor = db.rawQuery(sql, null);
                                if (cursor.getCount() > 0) {
                                    while (cursor.moveToNext()) {
                                        sql = "UPDATE qa_zeval_det SET valor_fecha='" + simpleFormat.format(date) + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                        db.execSQL(sql);
                                    }
                                } else {
                                    sql = "INSERT INTO qa_zeval_det (id_evaluacion, id_campo, valor_fecha ) VALUES ('" + id_evaluacion + "', '" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "','" + simpleFormat.format(date) + "' )";
                                    db.execSQL(sql);
                                }
                            }
                        });


                //holder.spinnerFR.setVisibility(View.GONE);

                break;
            case "TEXT":
                holder.editTextFR.setVisibility(View.VISIBLE);
                sql = "SELECT valor_cadena FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                cursor = db.rawQuery(sql, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        holder.editTextFR.setText(cursor.getString(0));
                    }
                }


//                txtWatcher = new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                        isOnTextChanged = true;
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//                        if (isOnTextChanged) {
//                            isOnTextChanged = false;
//                            sql = "SELECT id_campo FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
//                            cursor = db.rawQuery(sql, null);
//                            if (cursor.getCount() > 0) {
//                                while (cursor.moveToNext()) {
//                                    sql = "UPDATE qa_zeval_det SET valor_cadena='" + holder.editTextFR.getText().toString() + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
//                                    db.execSQL(sql);
//                                    Log.d(TAG, dataSet.get(holder.getAdapterPosition()).getTipo() + "-->" + sql);
//                                }
//                            } else {
//                                sql = "INSERT INTO qa_zeval_det (id_evaluacion, id_campo, valor_cadena ) VALUES ('" + id_evaluacion + "', '" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "','" + holder.editTextFR.getText().toString() + "' )";
//                                db.execSQL(sql);
//                                Log.d(TAG, dataSet.get(holder.getAdapterPosition()).getTipo() + "-->" + sql);
//                            }
//                        }
//                    }
//                };
//                holder.editTextFR.addTextChangedListener(txtWatcher);


                //holder.editTextFR.addTextChangedListener(txtWatcher);
                //txtWatcher=null;


                //holder.editTextFR.removeTextChangedListener();

                holder.editTextFR.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if (!hasFocus) {
                            sql = "SELECT id_campo FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                            cursor = db.rawQuery(sql, null);
                            if (cursor.getCount() > 0) {
                                while (cursor.moveToNext()) {
                                    sql = "UPDATE qa_zeval_det SET valor_cadena='" + holder.editTextFR.getText().toString() + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                    db.execSQL(sql);
                                    Log.d(TAG, sql);
                                }
                            } else {
                                sql = "INSERT INTO qa_zeval_det (id_evaluacion, id_campo, valor_cadena ) VALUES ('" + id_evaluacion + "', '" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "','" + holder.editTextFR.getText().toString() + "' )";
                                db.execSQL(sql);
                                Log.d(TAG, sql);
                            }
                            fields.put(Integer.toString(dataSet.get(holder.getAdapterPosition()).getId_campo()), holder.editTextFR.getText().toString());
                        }
                    }
                });


                break;
            case "NUMB":

                holder.editTextFR.setVisibility(View.VISIBLE);
                holder.editTextFR.setInputType(InputType.TYPE_CLASS_NUMBER);

                holder.editTextFR.setVisibility(View.VISIBLE);
                sql = "SELECT valor_numerico FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                cursor = db.rawQuery(sql, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        holder.editTextFR.setText(cursor.getString(0));
                    }
                }


                holder.editTextFR.setOnFocusChangeListener(new View.OnFocusChangeListener() {


                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if (!hasFocus) {
                            sql = "SELECT id_campo FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                            cursor = db.rawQuery(sql, null);
                            if (cursor.getCount() > 0) {
                                while (cursor.moveToNext()) {
                                    sql = "UPDATE qa_zeval_det SET valor_numerico='" + holder.editTextFR.getText().toString() + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                    db.execSQL(sql);
                                    Log.d(TAG, sql);
                                }
                            } else {
                                sql = "INSERT INTO qa_zeval_det (id_evaluacion, id_campo, valor_numerico ) VALUES ('" + id_evaluacion + "', '" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "','" + holder.editTextFR.getText().toString() + "' )";
                                db.execSQL(sql);
                                Log.d(TAG, sql);
                            }
                            fields.put(Integer.toString(dataSet.get(holder.getAdapterPosition()).getId_campo()), holder.editTextFR.getText().toString());
                        }
                    }
                });

//                holder.editTextFR.setFilters(new InputFilter[] {new InputFilter.LengthFilter(dataSet.get(holder.getAdapterPosition()).getLongt())});
                break;
            case "SELECT":

                holder.spinnerFR.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus) {
                            HideKeyboard(holder.spinnerFR);
                        }
                    }
                });

                ArrayList<String> result = new ArrayList<>();
                ArrayAdapter<String> adapter;
                switch (dataSet.get(holder.getAdapterPosition()).getEtiqueta()) {
                    case "Estado":
                        /*LLENADO DEL SPINNER CON ESTADOS*/
                        sql = "SELECT `descripcion` FROM `qa_cat_estados`";
                        cursor = db.rawQuery(sql, null);
                        result.add("");
                        while (cursor.moveToNext()) {
                            result.add(cursor.getString(0));
                        }
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item
                                , result);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        holder.spinnerFR.setAdapter(adapter);

                        /*SELECCIONO EL ITEM EN EL SPINNER SEGUN LA BD*/
                        sql = "SELECT valor_cadena FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                        cursor = db.rawQuery(sql, null);
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                holder.spinnerFR.setSelectedIndex(adapter.getPosition(cursor.getString(0)));
                                resultMunicipios.clear();
                                sql = "SELECT \n" +
                                        "  `qa_cat_municipios`.`municipio`\n" +
                                        "FROM\n" +
                                        "  `qa_cat_estados`\n" +
                                        "  INNER JOIN `qa_cat_municipios` ON (`qa_cat_estados`.`id_estado` = `qa_cat_municipios`.`id_estado`)\n" +
                                        "WHERE\n" +
                                        "  `qa_cat_estados`.`descripcion` = '" + cursor.getString(0) + "'";
                                cursor = db.rawQuery(sql, null);
                                resultMunicipios.add("");
                                while (cursor.moveToNext()) {
                                    resultMunicipios.add(cursor.getString(0));
                                }
                            }
                        }
                        /*
                        Al seleccionar el estado, se actualiza o inserta
                        se llena el arraylist de los municipios
                         */
                        holder.spinnerFR.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                                //municipiosSpinner.setAdapter(null);
                                sql = "SELECT id_campo FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                cursor = db.rawQuery(sql, null);
                                if (cursor.getCount() > 0) {
                                    while (cursor.moveToNext()) {
                                        sql = "UPDATE qa_zeval_det SET valor_cadena='" + item.toString() + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                        db.execSQL(sql);
                                        Log.d(TAG, sql);
                                    }
                                } else {
                                    sql = "INSERT INTO qa_zeval_det (id_evaluacion, id_campo, valor_cadena ) VALUES ('" + id_evaluacion + "', '" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "','" + item.toString() + "' )";
                                    db.execSQL(sql);
                                    Log.d(TAG, sql);
                                }
                                fields.put(Integer.toString(dataSet.get(holder.getAdapterPosition()).getId_campo()), item.toString());
                                resultMunicipios.clear();
                                sql = "SELECT \n" +
                                        "  `qa_cat_municipios`.`municipio`\n" +
                                        "FROM\n" +
                                        "  `qa_cat_estados`\n" +
                                        "  INNER JOIN `qa_cat_municipios` ON (`qa_cat_estados`.`id_estado` = `qa_cat_municipios`.`id_estado`)\n" +
                                        "WHERE\n" +
                                        "  `qa_cat_estados`.`descripcion` = '" + item.toString() + "'";
                                cursor = db.rawQuery(sql, null);
                                resultMunicipios.add("");
                                while (cursor.moveToNext()) {
                                    resultMunicipios.add(cursor.getString(0));

                                }
                                if (municipiosSpinner != null) {
                                    ArrayAdapter adapter = new ArrayAdapter<>(context,
                                            android.R.layout.simple_spinner_item, Collections.emptyList());
                                    municipiosSpinner.setAdapter(adapter);

                                    adapterMunicipios = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item
                                            , resultMunicipios);
                                    adapterMunicipios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    municipiosSpinner.setAdapter(adapterMunicipios);
                                }


                            }
                        });

                        break;
                    case "Municipio":
                        /*
                        Se llena el spinner de los municipios, seleccionando
                         */
                        municipiosSpinner = holder.spinnerFR;
                        adapterMunicipios = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item
                                , resultMunicipios);
                        adapterMunicipios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        holder.spinnerFR.setAdapter(adapterMunicipios);
                        if (adapterMunicipios.getCount() > 0) {
                            holder.spinnerFR.setSelectedIndex(0);
                        }

                        sql = "SELECT valor_cadena FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                        cursor = db.rawQuery(sql, null);
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                int spinnerPos = adapterMunicipios.getPosition(cursor.getString(0));
                                if (spinnerPos >= 0) {
                                    holder.spinnerFR.setSelectedIndex(spinnerPos);
                                } else {
                                    holder.spinnerFR.setSelectedIndex(0);
                                }
                            }
                        }

                        holder.spinnerFR.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                                sql = "SELECT id_campo FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                cursor = db.rawQuery(sql, null);
                                if (cursor.getCount() > 0) {
                                    while (cursor.moveToNext()) {
                                        sql = "UPDATE qa_zeval_det SET valor_cadena='" + item.toString() + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                        db.execSQL(sql);
                                        Log.d(TAG, sql);
                                    }
                                } else {
                                    sql = "INSERT INTO qa_zeval_det (id_evaluacion, id_campo, valor_cadena ) VALUES ('" + id_evaluacion + "', '" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "','" + item.toString() + "' )";
                                    db.execSQL(sql);
                                    Log.d(TAG, sql);
                                }
                                fields.put(Integer.toString(dataSet.get(holder.getAdapterPosition()).getId_campo()), item.toString());
                            }
                        });


                        break;
                    default:

                        /*SPINNERS*/
                        sql = "SELECT   `qa_comb`.`etiqueta` " +
                                "FROM `qa_camp` " +
                                "INNER JOIN `qa_comb` ON (`qa_camp`.`id_campo` = `qa_comb`.`id_campo`) " +
                                "WHERE   `qa_camp`.`id_servicio` = " + dataSet.get(holder.getAdapterPosition()).getId_servicio() + " AND   `qa_camp`.`id_campo` = " + dataSet.get(holder.getAdapterPosition()).getId_campo();
                        cursor = db.rawQuery(sql, null);
                        result.add("");
                        while (cursor.moveToNext()) {
                            result.add(cursor.getString(0));
                        }
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item
                                , result);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        holder.spinnerFR.setAdapter(adapter);


                        sql = "SELECT valor_cadena FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                        cursor = db.rawQuery(sql, null);
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                holder.spinnerFR.setSelectedIndex(adapter.getPosition(cursor.getString(0)));
                            }
                        }


                        holder.spinnerFR.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                                sql = "SELECT id_campo FROM qa_zeval_det WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                cursor = db.rawQuery(sql, null);
                                if (cursor.getCount() > 0) {
                                    while (cursor.moveToNext()) {
                                        sql = "UPDATE qa_zeval_det SET valor_cadena='" + item.toString() + "' WHERE id_evaluacion='" + id_evaluacion + "' AND id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'";
                                        db.execSQL(sql);
                                        Log.d(TAG, sql);
                                    }
                                } else {
                                    sql = "INSERT INTO qa_zeval_det (id_evaluacion, id_campo, valor_cadena ) VALUES ('" + id_evaluacion + "', '" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "','" + item.toString() + "' )";
                                    db.execSQL(sql);
                                    Log.d(TAG, sql);
                                }
                                fields.put(Integer.toString(dataSet.get(holder.getAdapterPosition()).getId_campo()), item.toString());
                            }
                        });

                        break;
                }
                holder.spinnerFR.setVisibility(View.VISIBLE);


                break;
        }
        if (fields != null){
            fields.forEach((key, value) -> System.out.println("[Key] : " + key + " [Value] : " + value));
        }

//        cursor = db.rawQuery(
//                "SELECT " + campo + " " +
//                        "FROM qa_zeval_det " +
//                        "WHERE " +
//                        "id_evaluacion_fb='" + idE + "' AND " +
//                        "id_campo='" + dataSet.get(holder.getAdapterPosition()).getId_campo() + "'",
//                null);
//        while (cursor.moveToNext()) {
//            holder.editTextFR.setText(cursor.getString(0));
//        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtEtiquetaFR;
        private final EditText editTextFR;
        private final MaterialSpinner spinnerFR;
        private final Button buttonFR;

        public ViewHolder(View item) {
            super(item);
            txtEtiquetaFR = item.findViewById(R.id.txtEtiquetaFR);
            editTextFR = item.findViewById(R.id.editTextFR);
            spinnerFR = item.findViewById(R.id.spinnerFR);
            buttonFR = item.findViewById(R.id.buttonFR);
        }
    }
    private void HideKeyboard(View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }
    private void saveAfertFocus(View view) {
        view.clearFocus();
    }
}
