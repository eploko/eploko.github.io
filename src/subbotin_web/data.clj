(ns subbotin-web.data
  (:require
   [clojure.java.shell :refer [sh]]
   [clojure.string :as str]))

(def site-title
  "Andrey Subbotin")

(def email
  "andrey@subbotin.me")

(def elsewhere
  "My social accounts."
  [{:href "https://github.com/eploko"
    :icon [:fab :fa-github]}
   {:href "https://stackoverflow.com/users/355357/eploko"
    :icon [:fab :fa-stack-overflow]}
   {:href "https://dribbble.com/eploko"
    :icon [:fab :fa-dribbble]}
   {:href "https://www.linkedin.com/in/asubbotin/"
    :icon [:fab :fa-linkedin]}])

(def pgp-fingerprint
  (str/trim-newline (:out (sh "gpg" "--fingerprint" email))))

(def pgp-public-key
  (:out (sh "gpg" "--armor" "--export" email)))

(def pgp-fingerprint-parts
  (->> (re-seq #"[0-9A-F]{4}\s" pgp-fingerprint)
     (drop 1)
     (take 10)
     (map str/trim)
     (partition 5)
     (map (partial str/join " "))
     vec))

(def timeline
  [{:when "May 14th, 1981"
    :what "Respawned into the amazing world at [56.01722131572176, 92.79474952035382](https://goo.gl/maps/7yLr2JbN3vidXTgn8)."}
   {:when "1987"
    :what "Discovered computer games in a local arcade. There were several Ataris there and I and my friends spent many hours there playing [Fort Apocalypse](https://en.wikipedia.org/wiki/Fort_Apocalypse)."}
   {:when "June 1992"
    :what "Played with an Apple computer—Macintosh Centris 610—and Adobe Photoshop for the first time in a local computer expo."}
   {:when "September 1992"
    :what "Started learning Basic on a Soviet-era [UKNC-0511](https://en.wikipedia.org/wiki/UKNC)."}
   {:when "December 1992"
    :what "Persuaded my mom to buy me an [Electronika MS 1502](https://ru.wikipedia.org/wiki/Электроника_МС_1502). Unfortunately there was no way to connect it to our old Soviet black-and-white Rassvet 307-1 TV set. Still I managed to write some simple music-generating programs on it, even without being able to see a single character of the Basic code."}
   {:when "September 1993"
    :what "Immersed myself into Turbo Pascal."}
   {:when "November 1993"
    :what "Got introduced to Turbo C and never looked back."}
   {:when "July 1994"
    :what "Spent three weeks in a computer summer camp mesmerized by a [SGI Indigo<sup>2</sup>](https://en.wikipedia.org/wiki/SGI_Indigo²_and_Challenge_M) and [IRIX](https://en.wikipedia.org/wiki/IRIX). Learnt what `ps ax` meant, how to `kill` stuck processes, and how to clean space by removing core dumps from crashed software."}
   {:when "February 1998"
    :what "My uncle gave me my first PC—a 60Mhz Pentium with 4MB RAM and a 512MB HDD—and a HP LaserJet printer. He hadn't given me a monitor though and I was too poor to buy one. So for a while the Prt Scrn key became my good friend."}
   {:when "August 15th, 1998"
    :what "Just two days before [the Russian Rouble collapsed](https://en.wikipedia.org/wiki/1998_Russian_financial_crisis) my mom bought me a 15-inch LG Flatron monitor. Had it been two days later, we wouldn't be able to offord one."}
   {:when "Beginning of October 1998"
    :what "A friend happened to rent an apartment without a phone line and lent me his modem. After spending some time on the local BBSes I managed to become a point in FidoNet. 2:5090/47.41 was the address. "}
   {:when "Near the end of October 1998"
    :what "Got my first job as a web master. Occupied myself with all things Perl."}
   {:when "February 1999"
    :what "Became a proper FidoNet node, 2:5090/114. The station answered calls 24×7 and my mom had to put our home phone into the silent mode."}
   {:when "March 2001"
    :what "Lended a web master job at a local advertising agency. ASP, Perl, PHP, [Parser](https://www.parser.ru/en/), MSSQL, PostgreSQL and a ton of other technologies were together in daily use and I had to figure out all of them."}
   {:when "May 15th, 2002"
    :what "The agency was also a kind of ISP and they were desperetely in need of a billing solution for their clients, which I ended up developing full-time on top of Linux, iptables, [NeTraMet](https://www.caida.org/tools/measurement/netramet/), and PostgreSQL. The result even gained me [the 1st Place Award at the Soft-Parad student software contest](https://www.soft-parade.ru/node/362)."}])
