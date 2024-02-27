package com.example.housemaster

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentBookAppointmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class BookAppointmentFragment : Fragment(R.layout.fragment_book_appointment) {

    private lateinit var bookAppointmentBinding: FragmentBookAppointmentBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private val args: BookAppointmentFragmentArgs by navArgs()
    var bookedTimeSlots: Array<String> = arrayOf()
    private var TimeSlotRecyclerViewAdapter: BookAppointmentTimeSlotAdapter? = null
    private var bookAppTimeSlot = ""
    private lateinit var sharedPreferences: SharedPreferences
    private var isHomeServiceEnable = false
    private lateinit var serviceProviderIdSharedPre: String

    private lateinit var timeSlotRecyclerView: RecyclerView
    private var timeSLotArrayList = ArrayList<BookAppointmentTimeSlotModel>()
    private var bookedTimeSLotArrayList = ArrayList<BookAppointmentTimeSlotModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseFirestore = FirebaseFirestore.getInstance()
        //shared preference
        sharedPreferences = requireContext().getSharedPreferences(
            "com.example.housemaster",
            Context.MODE_PRIVATE
        )

        super.onViewCreated(view, savedInstanceState)
        bookAppointmentBinding = FragmentBookAppointmentBinding.bind(view)
        bookAppointmentBinding.timeSlotSelectDateFalse.visibility = View.VISIBLE
        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE


//date time
        var myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            Log.d("from date picker ", "Current value:  " + Calendar.MONTH)
            updateLable(myCalender)

        }
//date picker
        bookAppointmentBinding.aptServiceDate.setOnClickListener {

            // Create and show the DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                datePicker,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)
            )

            // Set a minimum date to disable previous dates
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

            datePickerDialog.show()

        }
        //getServiceTimingData()
        //continue button
        bookAppointmentBinding.btnCheckout.setOnClickListener {
            if (bookAppTimeSlot.isNotEmpty() && bookAppTimeSlot != "" && bookAppointmentBinding.aptServiceDate.text.toString() != "Select Date") {

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
                    "Select Date and Time Slot to continue",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    }


    private fun updateLable(myCalender: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.CANADA)
        Log.d("time before set ", "Current value:  " + sdf.format(myCalender.time))
        getRespectiveDayFromTheDate(sdf.format(myCalender.time))
        bookAppointmentBinding.aptServiceDate.setText(sdf.format(myCalender.time))

    }

    private fun getRespectiveDayFromTheDate(format: String) {


        Log.d("time after set ", "Current value:  " + format)
        var dateSelected = format

        // Define the date format
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        try {
            // Parse the date string
            val date = dateFormat.parse(dateSelected)

            // Format the date to get the day name
            val dayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)

            // Print the day name
            Log.d("day name", "Current value:  " + dayName)
            bookAppointmentBinding.timeSlotSelectDateFalse.visibility = View.GONE
            getServiceTimingData(dayName, dateSelected)
        } catch (e: Exception) {
            println("Error parsing the date: ${e.message}")
        }
    }

    private fun getServiceTimingData(dayName: String, dateSelected: String) {

        timeSlotRecyclerView = bookAppointmentBinding.bookAptTimeSlotsRV
        bookedTimeSLotArrayList.clear()
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)
        timeSlotRecyclerView!!.layoutManager = layoutManager

        serviceProviderIdSharedPre =
            sharedPreferences.getString("service_provider_id", "").toString()
        timeSLotArrayList = arrayListOf<BookAppointmentTimeSlotModel>()

        timeSLotArrayList.clear()
        val ref =
            firebaseFirestore.collection("timings").document(serviceProviderIdSharedPre)

        ref.get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {

                val dataArray = documentSnapshot.get("timings") as HashMap<*, *>

                val value = dataArray[dayName] as ArrayList<Any>


                if (value != null) {
                    if (value[0].toString()
                            .substring(value[0].toString().length - 2) == "AM" && value[1].toString()
                            .substring(value[1].toString().length - 2) == "PM"
                    ) {
                        val startHour = value[0].toString().substring(0, 2).toString().toInt()
                        var endHour = value[1].toString().substring(0, 2).toString().toInt()
                        if (endHour == 1) {
                            endHour = 13
                        } else if (endHour == 2) {
                            endHour = 14
                        } else if (endHour == 3) {
                            endHour = 15
                        } else if (endHour == 4) {
                            endHour = 16
                        } else if (endHour == 5) {
                            endHour = 17
                        } else if (endHour == 6) {
                            endHour = 18
                        } else if (endHour == 7) {
                            endHour = 19
                        } else if (endHour == 8) {
                            endHour = 20
                        } else if (endHour == 9) {
                            endHour = 21
                        } else if (endHour == 10) {
                            endHour = 22
                        } else if (endHour == 11) {
                            endHour = 23
                        } else if (endHour == 12) {
                            endHour = 24
                        }

                        for (i in startHour..endHour - 1) {
                            val timeSlot = "" + i + ".00 - " + (i + 1) + ".00"

                            val services = BookAppointmentTimeSlotModel(
                                timeSlot
                            )
                            timeSLotArrayList.add(services)

                        }

                        Log.d("YourTag", "Current value:  $timeSLotArrayList")


                    } else if (value[0].toString()
                            .substring(value[0].toString().length - 2) == "PM" && value[1].toString()
                            .substring(value[1].toString().length - 2) == "PM"
                    ) {
                        var startHour = value[0].toString().substring(0, 2).toString().toInt()


                        var endHour = value[1].toString().substring(0, 2).toString().toInt()

                        if (startHour == 1) {
                            startHour = 13
                        } else if (startHour == 2) {
                            startHour = 14
                        } else if (startHour == 3) {
                            startHour = 15
                        } else if (startHour == 4) {
                            startHour = 16
                        } else if (startHour == 5) {
                            startHour = 17
                        } else if (startHour == 6) {
                            startHour = 18
                        } else if (startHour == 7) {
                            startHour = 19
                        } else if (startHour == 8) {
                            startHour = 20
                        } else if (startHour == 9) {
                            startHour = 21
                        } else if (startHour == 10) {
                            startHour = 22
                        } else if (startHour == 11) {
                            startHour = 23
                        } else if (startHour == 12) {
                            startHour = 24
                        }

                        if (endHour == 1) {
                            endHour = 13
                        } else if (endHour == 2) {
                            endHour = 14
                        } else if (endHour == 3) {
                            endHour = 15
                        } else if (endHour == 4) {
                            endHour = 16
                        } else if (endHour == 5) {
                            endHour = 17
                        } else if (endHour == 6) {
                            endHour = 18
                        } else if (endHour == 7) {
                            endHour = 19
                        } else if (endHour == 8) {
                            endHour = 20
                        } else if (endHour == 9) {
                            endHour = 21
                        } else if (endHour == 10) {
                            endHour = 22
                        } else if (endHour == 11) {
                            endHour = 23
                        } else if (endHour == 12) {
                            endHour = 24
                        }

                        for (i in startHour..endHour - 1) {
                            val timeSlot = "" + i + ".00 - " + (i + 1) + ".00"

                            val services = BookAppointmentTimeSlotModel(
                                timeSlot
                            )
                            timeSLotArrayList.add(services)

                        }

                        Log.d("YourTag", "Current value:  $timeSLotArrayList")


                    } else if (value[0].toString()
                            .substring(value[0].toString().length - 2) == "PM" && value[1].toString()
                            .substring(value[1].toString().length - 2) == "AM"
                    ) {
                        var startHour = value[0].toString().substring(0, 2).toString().toInt()


                        var endHour = value[1].toString().substring(0, 2).toString().toInt()

                        if (startHour == 1) {
                            startHour = 13
                        } else if (startHour == 2) {
                            startHour = 14
                        } else if (startHour == 3) {
                            startHour = 15
                        } else if (startHour == 4) {
                            startHour = 16
                        } else if (startHour == 5) {
                            startHour = 17
                        } else if (startHour == 6) {
                            startHour = 18
                        } else if (startHour == 7) {
                            startHour = 19
                        } else if (startHour == 8) {
                            startHour = 20
                        } else if (startHour == 9) {
                            startHour = 21
                        } else if (startHour == 10) {
                            startHour = 22
                        } else if (startHour == 11) {
                            startHour = 23
                        } else if (startHour == 12) {
                            startHour = 24
                        }



                        for (i in startHour..endHour - 1) {
                            val timeSlot = "" + i + ".00 - " + (i + 1) + ".00"

                            val services = BookAppointmentTimeSlotModel(
                                timeSlot
                            )
                            timeSLotArrayList.add(services)

                        }

                        Log.d("YourTag", "Current value:  $timeSLotArrayList")


                    }

                }

//getting the time slots from appointments

                val ref =
                    firebaseFirestore.collection("appointments")
                        .whereEqualTo("apptDate", dateSelected)

                ref.get().addOnSuccessListener { documentSnapshot ->
                    bookedTimeSlots = emptyArray()
                    if (!documentSnapshot.isEmpty) {
                        val documents = documentSnapshot.documents
                        for (category in documents) {
                            bookedTimeSlots = bookedTimeSlots.plus(category["aptTime"].toString())

                        }
                    }

                    // Log.d("booked time slot string array", "Current value:  $timeSLotArrayList")
                    for (item in bookedTimeSlots.indices) {
                        val services = BookAppointmentTimeSlotModel(
                            bookedTimeSlots[item]
                        )
                        bookedTimeSLotArrayList.add(services)
                    }

                    bookedTimeSlots = emptyArray()
                    //Log.d("booked time slot List", "Current value:  $timeSLotArrayList")
                    // Log.d("after remove things", "Current value:  $timeSLotArrayList")
                    /*val newArray =
                        convertedBookTimeArrayList.toSet().subtract(timeSLotArrayList.toSet())
                            .toTypedArray()*/


                    /* Log.d(
                         "booked time slots before changing",
                         "Current value:  $bookedTimeSLotArrayList"
                     )*/
                    // Log.d("time slots before changing", "Current value:  $timeSLotArrayList")

                    if (timeSLotArrayList.isNotEmpty()) {
                        timeSLotArrayList.removeAll(bookedTimeSLotArrayList.toSet())
                    }

                    bookedTimeSLotArrayList.clear()
                    //Log.d("after remove things", "Current value:  $timeSLotArrayList")

                    if (timeSLotArrayList.isEmpty()) {
                        bookAppointmentBinding.timeSlotSelectDateFalse.setText("Sorry!.No available time slots found, Please try selecting a new date.")
                        bookAppointmentBinding.timeSlotSelectDateFalse.visibility = View.VISIBLE
                        timeSLotArrayList.clear()
                        var adapter = BookAppointmentTimeSlotAdapter(timeSLotArrayList)
                        timeSlotRecyclerView.adapter = adapter
                        TimeSlotRecyclerViewAdapter?.updateItems(timeSLotArrayList)
                    } else {
                        bookAppointmentBinding.timeSlotSelectDateFalse.visibility = View.GONE
                        var adapter = BookAppointmentTimeSlotAdapter(timeSLotArrayList)
                        timeSlotRecyclerView.adapter = adapter
                        TimeSlotRecyclerViewAdapter?.updateItems(timeSLotArrayList)
                        adapter.setOnItemClickListener(object :
                            BookAppointmentTimeSlotAdapter.onItemClickListener {
                            override fun onItemClick(position: Int, clickedView: View) {
                                bookAppTimeSlot = timeSLotArrayList[position].timeSlotTitle

                                //bookAppointmentBinding.btnCheckout.setText(bookAppTimeSlot)
                            }
                        }
                        )
                    }


                }.addOnFailureListener {

                    Log.e("firebase", "Error getting data", it)
                }


                /*    var adapter = BookAppointmentTimeSlotAdapter(timeSLotArrayList)
                    timeSlotRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        BookAppointmentTimeSlotAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            bookAppTimeSlot = timeSLotArrayList[position].timeSlotTitle

                        }
                    }
                    )*/


            }

        }.addOnFailureListener {

            Log.e("firebase", "Error getting data", it)
        }


    }
}
