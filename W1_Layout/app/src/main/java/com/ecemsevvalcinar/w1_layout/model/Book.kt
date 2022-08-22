package com.ecemsevvalcinar.w1_layout.model


import android.os.Parcel
import android.os.Parcelable

class Book(val bookName: String?, val about: String?, val image: Int):Parcelable {


    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bookName)
        parcel.writeString(about)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }

}