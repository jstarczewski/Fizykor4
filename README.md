<img align="left" src="/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="">

# Fizykor

Fizykor to wzory fizyczne zawsze pod ręką  
  
[![Google Play](https://play.google.com/intl/en_us/badges/images/badge_new.png)](https://play.google.com/store/apps/details?id=com.clakestudio.pc.fizykor)

## About Fizykor
![Back](/images/back.PNG)

Third version of my most popular app relesed in Google Play Store back in 2016 now with new architecutre. This repository contains production version of Fizykor. The goal is to keep the production version as simple as possible and provide best user experience. [Here](https://github.com/jstarczewski/Fizykor3) is the alternative version of the app with the same functionality implemented using dependency injection framework and more Android Jetpack components.  
- [x] Kotlin
- [x] MVVM architecture
- [x] Room persistance Library
- [x] DataBinding library
- [x] RxJava | RxAndroid
- [x] MathView (my own third party view library for kotlin)
## Future of Fizykor
I set myself a goal to publish Fizykor in 2018. Now app is working, but there are several issues that I will add and then solve. The biggest problem is the amount of memory app needs. In the nearest furure I would like to:  
- [ ] Make Fizykro fragmentless
- [ ] Add remote Firebase database to sync equations
- [ ] Refactor animations to provide better user experience
- [ ] Test the app with Espresso, JUnit and Mockito

## No RecyclerView solution or RecyclerView solution?
After implementing equations inside RecyclerView or without RecyclerView the optimal solution that gives the best user experience is to display many equations in one RecyclerView item.
