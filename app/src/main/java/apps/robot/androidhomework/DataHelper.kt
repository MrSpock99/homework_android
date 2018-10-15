package apps.robot.androidhomework

fun getData(): ArrayList<User> {
    val list = ArrayList<User>()
    list.add(User(8, "Vladimir Putin 1", R.drawable.putin, "Nologi"))
    list.add(User(4, "Vladimir Putin 2", R.drawable.putin, "Nologi"))
    list.add(User(1, "Ayaz", R.drawable.ayaz, "Guitar,programming,eating"))
    list.add(User(5, "Vladimir Putin 5", R.drawable.putin, "Nologi"))
    list.add(User(2, "Batman", R.drawable.batman, "Fighting criminals"))
    list.add(User(3, "Venom", R.drawable.venom, "Eating people"))
    list.add(User(6, "Vladimir Putin 3", R.drawable.putin, "Nologi"))
    list.add(User(7, "Vladimir Putin 4", R.drawable.putin, "Nologi"))
    return list
}
