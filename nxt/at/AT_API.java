package nxt.at;

public abstract interface AT_API
{
  public abstract long get_A1(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_A2(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_A3(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_A4(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_B1(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_B2(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_B3(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_B4(AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_A1(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_A2(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_A3(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_A4(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_A1_A2(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_A3_A4(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_B1(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_B2(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_B3(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_B4(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_B1_B2(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_B3_B4(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State);
  
  public abstract void clear_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void clear_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void copy_A_From_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void copy_B_From_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract long check_A_Is_Zero(AT_Machine_State paramAT_Machine_State);
  
  public abstract long check_B_Is_Zero(AT_Machine_State paramAT_Machine_State);
  
  public abstract long check_A_equals_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void swap_A_and_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void add_A_to_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void add_B_to_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void sub_A_from_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void sub_B_from_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void mul_A_by_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void mul_B_by_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void div_A_by_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void div_B_by_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void or_A_with_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void or_B_with_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void and_A_with_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void and_B_with_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void xor_A_with_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void xor_B_with_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void MD5_A_to_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract long check_MD5_A_with_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void HASH160_A_to_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract long check_HASH160_A_with_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void SHA256_A_to_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract long check_SHA256_A_with_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_Block_Timestamp(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_Creation_Timestamp(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_Last_Block_Timestamp(AT_Machine_State paramAT_Machine_State);
  
  public abstract void put_Last_Block_Hash_In_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void A_to_Tx_after_Timestamp(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_Type_for_Tx_in_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_Amount_for_Tx_in_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_Timestamp_for_Tx_in_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_Random_Id_for_Tx_in_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void message_from_Tx_in_A_to_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void B_to_Address_of_Tx_in_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void B_to_Address_of_Creator(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_Current_Balance(AT_Machine_State paramAT_Machine_State);
  
  public abstract long get_Previous_Balance(AT_Machine_State paramAT_Machine_State);
  
  public abstract void send_to_Address_in_B(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void send_All_to_Address_in_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void send_Old_to_Address_in_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract void send_A_to_Address_in_B(AT_Machine_State paramAT_Machine_State);
  
  public abstract long add_Minutes_to_Timestamp(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State);
  
  public abstract void set_Min_Activation_Amount(long paramLong, AT_Machine_State paramAT_Machine_State);
  
  public abstract void put_Last_Block_Generation_Signature_In_A(AT_Machine_State paramAT_Machine_State);
  
  public abstract void SHA256_to_B(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State);
}


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_API
 * JD-Core Version:    0.7.1
 */