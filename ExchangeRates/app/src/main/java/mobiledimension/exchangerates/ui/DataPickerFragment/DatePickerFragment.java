package mobiledimension.exchangerates.ui.DataPickerFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import mobiledimension.exchangerates.R;
import mobiledimension.exchangerates.presenter.DatePickerFragment.FragmentView;
import mobiledimension.exchangerates.presenter.MainMenu.MainPresenter;
import mobiledimension.exchangerates.ui.MainMenu.MainView;

/**
 * Created by Турал on 28.11.2017.
 */

public class DatePickerFragment extends DialogFragment implements FragmentView, DatePickerDialog.OnDateSetListener {

    @Inject
    MainPresenter<MainView> mainPresenter;
    DatePickerDialog.OnDateSetListener listener;
    private Boolean isFirstCall = true; // Используется как костыль.   при нажатии на DataPicker он вызывается дважды. Исправлено в андроид 5.0
    private TextView CurrentDate;
    String formattedDate;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        final Calendar calendarMin = Calendar.getInstance(); // Дата для установки нижнего порога в DataPiker
        calendarMin.set(1999, 0, 1); // http://fixer.io/ хранит курсы валют начиная с 1999 года . Отсчет месяцев идет с нуля.

        //region получаем дату из TextView и парсим в DataPiker, чтобы при открытии диалога, он был наведен на установленную дату
        CurrentDate = (TextView) getActivity().findViewById(R.id.currentDateTextView);
        String Date = CurrentDate.getText().toString();
        if (!Date.isEmpty()) {
            String[] arrayDate = Date.split("-");
            year = Integer.parseInt(arrayDate[0]);
            month = Integer.parseInt(arrayDate[1]) - 1;
            day = Integer.parseInt(arrayDate[2]);
        }
        //endregion


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        DatePicker dp = datePickerDialog.getDatePicker();
        dp.setMinDate(calendarMin.getTimeInMillis()); // ставим нижний диапазон в DatePiker
        dp.setMaxDate(System.currentTimeMillis() + 5000); // верхний диапазон   в DatePiker устанавливаем сегодняшним днем
        //к нему прибавлены 5 секунд, потому что на андароид 5.1.1 верхним пределом ставится не текущий день, а точное текущее время вплоть до миллисекунд.
        // Поэтому это 5 секунд времени, чтобы успеть вернуться к выбору текущей даты.

        return datePickerDialog;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        isFirstCall = true;   // возвращаем исходное значение
    }



    @Override
    public String getDate() {
        return formattedDate;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (isFirstCall) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = sdf.format(calendar.getTime());
            CurrentDate.setText(formattedDate);
            mainPresenter.onDatePicked();

            isFirstCall = false;
        }
    }
}
