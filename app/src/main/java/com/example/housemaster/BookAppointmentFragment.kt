package com.example.housemaster

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
    lateinit var timeSlot: Array<String>
    private var TimeSlotRecyclerViewAdapter: BookAppointmentTimeSlotAdapter? = null
    private var bookAppTimeSlot = ""
    private lateinit var sharedPreferences: SharedPreferences
    private var isHomeServiceEnable = false
    private lateinit var serviceProviderIdSharedPre: String

    private lateinit var timeSlotRecyclerView: RecyclerView
    private var timeSLotArrayList = ArrayList<BookAppointmentTimeSlotModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseFirestore = FirebaseFirestore.getInstance()
        //shared preference
        sharedPreferences = requireContext().getSharedPreferences(
            "com.example.housemaster",
            Context.MODE_PRIVATE
        )
        super.onViewCreated(view, savedInstanceState)
        bookAppointmentBinding = FragmentBookAppointmentBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE


        /* timeSlot = arrayOf(
             "9AM - 10AM",
             "10AM - 11AM",
             "11AM - 12PM",
             "12PM - 01PM",
             "01PM - 02PM",
             "02PM - 03PM",
             "03PM - 04PM",
             "04PM - 05PM",

             )*/





        //getServiceTypeData()


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

            // Check if the selected date is before the current date
            /* if (myCalender.before(Calendar.getInstance())) {
                 // If it's before the current date, show an error message or take appropriate action
                 // For example, you can show a Toast message
                 Toast.makeText(
                     requireContext(),
                     "Please select a future date",
                     Toast.LENGTH_SHORT
                 ).show()

                 // Reset the Calendar instance to the current date
                 myCalender = Calendar.getInstance()
             } else {
                 // If it's a valid date, update the label or perform any other actions
                 updateLable(myCalender)
             }*/
            updateLable(myCalender)
            //updateLable(myCalender)
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

        timeSlotRecyclerView = bookAppointmentBinding.bookAptTimeSlotsRV
        TimeSlotRecyclerViewAdapter = BookAppointmentTimeSlotAdapter(timeSLotArrayList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)
        timeSlotRecyclerView!!.layoutManager = layoutManager
        timeSlotRecyclerView!!.adapter = TimeSlotRecyclerViewAdapter

        getServiceTimingData()


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


    /*  private fun getServiceTypeData() {
          for (i in timeSlot.indices) {
              val data = BookAppointmentTimeSlotModel(timeSlot[i])
              timeSLotArrayList.add(data)
          }

      }
  */

    private fun getServiceTimingData() {

        serviceProviderIdSharedPre =
            sharedPreferences.getString("service_provider_id", "").toString()
        timeSLotArrayList = arrayListOf<BookAppointmentTimeSlotModel>()

        val ref =
            firebaseFirestore.collection("timings").document(serviceProviderIdSharedPre)

        ref.get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {

                val dataArray = documentSnapshot.get("timings") as HashMap<*, *>

                val value = dataArray["Friday"] as ArrayList<Any>


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
                            val timeSlot = "" + i + " - " + (i + 1)

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
                            val timeSlot = "" + i + " - " + (i + 1)

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
                            val timeSlot = "" + i + " - " + (i + 1)

                            val services = BookAppointmentTimeSlotModel(
                                timeSlot
                            )
                            timeSLotArrayList.add(services)

                        }

                        Log.d("YourTag", "Current value:  $timeSLotArrayList")


                    }

                }

                var adapter = BookAppointmentTimeSlotAdapter(timeSLotArrayList)
                timeSlotRecyclerView.adapter = adapter
                adapter.setOnItemClickListener(object :
                    BookAppointmentTimeSlotAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        bookAppTimeSlot = timeSLotArrayList[position].timeSlotTitle

                    }
                }
                )

                /*  val services = BookAppointmentTimeSlotModel(
                        dataArray2["price"].toString(),
                        dataArray2["serviceName"].toString(),
                        dataArray2["price"].toString().toDouble(),
                    )
                    timeSLotArrayList.add(services)*/


            }

        }.addOnFailureListener {

            Log.e("firebase", "Error getting data", it)
        }


    }
}
