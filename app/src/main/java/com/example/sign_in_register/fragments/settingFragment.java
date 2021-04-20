package com.example.sign_in_register.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sign_in_register.AdminPage;
import com.example.sign_in_register.Item;
import com.example.sign_in_register.ItemAdapter;
import com.example.sign_in_register.R;
import com.example.sign_in_register.stylefile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class settingFragment extends Fragment {

    SeekBar seekbar;
    ImageView logo;
    private Button reset, apply;
    private Spinner Theme,Fontstyle;
    int numTaps,changing_size,origin_size,cur_fontstyle,fontstyle; // The number of times the user has tapped the logo. currently font size
    View view;
    TextView displaytext;
    String theme,cur_theme;
    stylefile user;

    // create and set spinner array
    final int[] Fontstylearray = {0,1,2,3};
    final String[] Themearray = {"#FFFFFFFF","#FF0000","#FF018786","#FF6200EE"};

    //create List view and save as item object
    private List<Item> list = new ArrayList<Item>();
    private ListView listView;
    private ItemAdapter itemAdapter;

    Dialog dialog;
    View fontsizeview,themedialog;

    public settingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  read stylefile object from database, use object user to accept    !!!!


        // Inflate the layout for this fragment, dialog layout
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        fontsizeview = inflater.inflate(R.layout.fontsizedialog, (ViewGroup)container.findViewById(R.id.dialogelement));
        themedialog = inflater.inflate(R.layout.themedialog, (ViewGroup)container.findViewById(R.id.dialogelement2));

        // read spinner object and set adapter to it
        Theme = (Spinner)themedialog.findViewById(R.id.theme);
        Theme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cur_theme = Themearray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // we do nothing at here, leave it blank
            }
        });


        Fontstyle = (Spinner)themedialog.findViewById(R.id.fontstyle);
        Fontstyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cur_fontstyle = Fontstylearray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // we do nothing at here, leave it blank
            }
        });

        // calling init method
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        numTaps = 0;
        logo = getActivity().findViewById(R.id.SJOG_Logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numTaps++;
                if(numTaps >= 3) {
                    Intent admin = new Intent(getActivity(), AdminPage.class);
                    startActivity(admin);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        numTaps = 0;
    }

    private void init()
    {
        //adapter set
        listView=(ListView)view.findViewById(R.id.Featrue_list);
        itemAdapter=new ItemAdapter(view.getContext(),R.layout.settingitem,list);
        listView.setAdapter(itemAdapter);

        //dialog init
        dialog = new Dialog(view.getContext());

        // set origin fontsize,fontstyle,backgroundcolor and read from database
        // variable name
        // fontstyle and theme as string
        // origin_size as int      !!!!


        //create item object without extraparameter, if you want with some other feature, use extra Parameter
        Item FontSize = new Item("FontSetting","Customize font size");
        Item Theme = new Item("Theme","Customize font style and Backgroundcolor");

        if(list.size() < 2)
        {
            //add to listview, remove this, each time we change into setting page it will add 3 more item.
            list.add(FontSize);
            list.add(Theme);
        }

        //create itemlistview listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // to let us know which one has been choosen
                switch (position){
                    case 0:{
                        //locate object
                        displaytext = (TextView)fontsizeview.findViewById(R.id.displaytext);
                        apply = (Button)fontsizeview.findViewById(R.id.Apply);
                        reset = (Button)fontsizeview.findViewById(R.id.Reset);
                        seekbar  = (SeekBar)fontsizeview.findViewById(R.id.textchange);
                        seekbar.setProgress(changing_size);

                        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                displaytext.setText("Currently font size: "+ seekBar.getProgress());
                                reset.setTextSize(seekbar.getProgress());
                                apply.setTextSize(seekbar.getProgress());
                                changing_size = seekbar.getProgress();
                                seekbar.setProgress(changing_size);
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                                // add method to do everything you want when you start moving seekbar
                                // no need for use so leave as nothing right now
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });     //seek bar set and closed

                        apply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                displaytext.setText("Set size " + seekbar.getProgress() +" as origin size");
                                origin_size = seekbar.getProgress();

                                stylefile sty = new stylefile(origin_size,fontstyle,cur_theme);

                                //do not forget to upload sty to user database here.    !!!!
                            }
                        });

                        reset.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                displaytext.setText("Reset to default size: " + origin_size);
                                reset.setTextSize(origin_size);
                                apply.setTextSize(origin_size);
                                seekbar.setProgress(origin_size);
                            }
                        });
                        dialog.setContentView(fontsizeview);
                        dialog.show();
                        break;
                    }

                    case 1:{
                        displaytext = (TextView)themedialog.findViewById(R.id.displaytext);
                        apply = (Button)themedialog.findViewById(R.id.Apply);
                        reset = (Button)themedialog.findViewById(R.id.Pre_view);
                        apply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fontstyle = cur_fontstyle;
                                theme = cur_theme;
                                stylefile sty = new stylefile(origin_size,fontstyle,cur_theme);

                                //do not forget to upload to user database here.    !!!!

                            }
                        });

                        reset.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                displaytext.setTypeface(null, cur_fontstyle);
                                displaytext.setBackgroundColor(Color.parseColor(cur_theme));
                            }
                        });

                        dialog.setContentView(themedialog);
                        dialog.show();
                        break;
                    }
                }   //close switch
            }
        });

    }   //inited
}