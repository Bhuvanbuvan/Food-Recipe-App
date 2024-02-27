package com.example.foodrecipe

class UserAccount {
    var userName:String?=null
    var userId:String?=null

    companion object{
        var instence:UserAccount?=null
            get(){
                if (field==null){
                    field= UserAccount()
                }
                return field
            }
            private set
    }
}