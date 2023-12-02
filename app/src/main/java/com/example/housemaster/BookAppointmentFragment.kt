package com.example.housemaster

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentBookAppointmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.YEAR
import java.util.Locale


class BookAppointmentFragment : Fragment(R.layout.fragment_book_appointment) {

    private lateinit var bookAppointmentBinding: FragmentBookAppointmentBinding
    private val args: BookAppointmentFragmentArgs by navArgs()
    lateinit var timeSlot: Array<String>
    private var TimeSlotRecyclerViewAdapter: BookAppointmentTimeSlotAdapter? = null
    private var bookAppTimeSlot = ""
    private var isHomeServiceEnable = false

    private lateinit var timeSlotRecyclerView: RecyclerView
    private var timeSLotArrayList = ArrayList<BookAppointmentTimeSlotModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookAppointmentBinding = FragmentBookAppointmentBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE



        timeSlot = arrayOf(
            "9AM - 10AM",
            "10AM - 11AM",
            "11AM - 12PM",
            "12PM - 01PM",
            "01PM - 02PM",
            "02PM - 03PM",
            "03PM - 04PM",
            "04PM - 05PM",

            )




        timeSlotRecyclerView = bookAppointmentBinding.bookAptTimeSlotsRV
        TimeSlotRecyclerViewAdapter = BookAppointmentTimeSlotAdapter(timeSLotArrayList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)
        timeSlotRecyclerView!!.layoutManager = layoutManager
        timeSlotRecyclerView!!.adapter = TimeSlotRecyclerViewAdapter


        getServiceTypeData()

        var adapter = BookAppointmentTimeSlotAdapter(timeSLotArrayList)
        timeSlotRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : BookAppointmentTimeSlotAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                bookAppTimeSlot = timeSLotArrayList[position].timeSlotTitle

            }
        }
        )

        //switch
        /*bookAppointmentBinding.aptServiceType.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // do something, the isChecked will be
            // true if the switch is in the On position
            if (isChecked) {
                isHomeServiceEnable = true

            }
        })*/


//date time
        var myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)


            updateLable(myCalender)
        }
//date picker
        bookAppointmentBinding.aptServiceDate.setOnClickListener {

            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)
            ).show()


        }

        //continue buton
        bookAppointmentBinding.btnCheckout.setOnClickListener {
            if (bookAppTimeSlot.isNotEmpty() || bookAppointmentBinding.aptServiceDate.text.toString() != "Select Date") {
                Toast.makeText(
                    requireContext(),
                    "" + isHomeServiceEnable + "" + bookAppTimeSlot + "" + bookAppointmentBinding.aptServiceDate.text.toString(),
                    Toast.LENGTH_LONG
                ).show()
                var date = bookAppointmentBinding.aptServiceDate.text.toString()
                val action =
                    BookAppointmentFragmentDirections.actionBookAppointmentFragmentToReviewBeforeConAppFragment(
                        bookAppTimeSlot,
                        isHomeServiceEnable,
                        date,
                        args.serviceTitle,
                        args.serviceTypeId
                    )
                findNavController().navigate(action)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Select Mandatory Fields to continue",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun updateLable(myCalender: Calendar) {
        val myFormat = "dd-mm-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.CANADA)
        bookAppointmentBinding.aptServiceDate.setText(sdf.format(myCalender.time))

    }


    private fun getServiceTypeData() {
        for (i in timeSlot.indices) {
            val data = BookAppointmentTimeSlotModel(timeSlot[i])
            timeSLotArrayList.add(data)
        }

    }
}