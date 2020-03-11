package com.example.safetravelsclient.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.safetravelsclient.commands.converters.ByteToUUIDConverterCommand;
import com.example.safetravelsclient.commands.converters.UUIDToByteConverterCommand;

import java.util.UUID;

public class UserTransition implements Parcelable
{
    private UUID id;
    public UUID getId() {return this.id;}
    public UserTransition setId(UUID id) {this.id = id; return this;}

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel destination, int flags)
    {
        //destination.writeByteArray(())
        destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
    }

    public UserTransition()
    {
        this.id = new UUID(0, 0);
    }

    public UserTransition(UserTransition user_transition)
    {
        this.id = user_transition.getId();
    }

    public UserTransition(Parcel user_transition_parcel) {this.id = (new ByteToUUIDConverterCommand().setValueToConvert(user_transition_parcel.createByteArray()).execute());}

    public static final Parcelable.Creator<UserTransition> CREATOR = new Parcelable.Creator<UserTransition>()
    {
        public UserTransition createFromParcel(Parcel userTransitionParcel)
        {
            return new UserTransition(userTransitionParcel);
        }

        public UserTransition[] newArray(int size) {return new UserTransition[size];}
    };
}

