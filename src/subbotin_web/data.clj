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
   {:when "September 1992"
    :what "Started learning Basic on a Soviet-era [UKNC-0511](https://en.wikipedia.org/wiki/UKNC)."}
   {:when "December 1992"
    :what "Persuaded my mom to buy me an [Electronika MS 1502](https://ru.wikipedia.org/wiki/Электроника_МС_1502). Unfortunately there was no way to connect it to our old Soviet black-and-white Rassvet 307-1 TV set. Still I managed to write some simple music-generating programs on it, even without being able to see a single character of the Basic code."}
   {:when "June 1993"
    :what "Played with an Apple computer—Macintosh Centris 610—and Adobe Photoshop for the first time in a local computer expo."}
   {:when "September 1993"
    :what "Immersed myself into Turbo Pascal."}
   {:when "November 1993"
    :what "Got introduced to Turbo C and never looked back."}
   {:when "December 1993"
    :what "Learnt enough assembler to interface with mouse drivers and run resident programs under DOS."}
   {:when "January 1994"
    :what "Ventured into implementing my own Norton Commander clone. I eventually abandoned the idea some months later, but still… had learnt heaps from the endeavour."}
   {:when "July 1994"
    :what "Spent three weeks in a computer summer camp for kids mesmerized by an [SGI Indigo<sup>2</sup>](https://en.wikipedia.org/wiki/SGI_Indigo²_and_Challenge_M) and [IRIX](https://en.wikipedia.org/wiki/IRIX). Learnt what `ps ax` meant, how to `kill` stuck processes, and how to free disk space by removing core dumps from crashed software."}
   {:when "April 1996"
    :what "Discovered [Watcom C](https://en.wikipedia.org/wiki/Watcom_C/C%2B%2B) and the wonderful possibilities provided by [the DOS/4GW DOS extender](https://en.wikipedia.org/wiki/DOS/4G)."}
   {:when "February 1998"
    :what "My uncle gave me my first PC—a 60Mhz Pentium with 4MB RAM and a 512MB HDD—and a HP LaserJet printer. He hadn't given me a monitor though and I was too poor to buy one. So for a while the Prt Scrn key became my good friend."}
   {:when "April 1998"
    :what "Wrote [Boa Vision](https://archive.org/details/boa_20201231)—a snake game for my girlfriend. All of the images were encoded in my own [RLE](https://en.wikipedia.org/wiki/Run-length_encoding)-based format and the game sported cool 8-bit tracker soundtrack and effects."}
   {:when "August 15th, 1998"
    :what "Just two days before [the Russian Rouble collapsed](https://en.wikipedia.org/wiki/1998_Russian_financial_crisis) my mom bought me a 15-inch LG Flatron monitor. Had it been two days later, we wouldn't be able to afford one."}
   {:when "End of August 1998"
    :what "Bought the 1st edition of the Java in a Nutshell book and fell in love with the language and the Write-Once-Run-Everywhere idea."}
   {:when "September 1998"
    :what "Obtained a Slackware Linux CD from a friend and installed it."}
   {:when "Beginning of October 1998"
    :what "A friend happened to rent an apartment without a phone line and lent me his modem. After spending some time on the local BBSes I managed to become a point in FidoNet. 2:5090/47.41 was the address. "}
   {:when "Near the end of October 1998"
    :what "Got my first job as a web master. Occupied myself with all things Perl."}
   {:when "1999"
    :what "My obsession with Java continued. I developed an app to build educational presentations. The UI was close to the Visual Basic suite with all the drag'n'drop mechanics and widgets, and the resulting presentations were serialized into standalone player JARs that could run on both Windows and Linux. The project was awarded [the 3rd Place Award at the Soft-Parade student software contest](https://www.soft-parade.ru/1999)."}
   {:when "September 22nd, 2000"
    :what "Became a proper FidoNet node, 2:5090/114. The station answered calls 24×7 and my mom had to put our home phone into the silent mode."}
   {:when "March 2001"
    :what "Landed a web master job at a local advertising agency. ASP, PHP, [Parser](https://www.parser.ru/en/), MSSQL, PostgreSQL and a ton of other technologies were together in daily use and I had to figure out all of them."}
   {:when "May 15th, 2002"
    :what "The company I was working for was a kind of ISP and they were desperetely in need of a billing solution for their clients, which I ended up developing full-time on top of Linux, iptables, [NeTraMet](https://www.caida.org/tools/measurement/netramet/), and PostgreSQL. The result even gained me [the 1st Place Award at the Soft-Parade student software contest](https://www.soft-parade.ru/node/362)."}
   {:when "February 2003"
    :what "Got hired by a team working on a binary Usenet groups aggregator service. I implemented lots of the backend and frontend mostly in Perl with occasional sprinkles of C: fetching of the news articles and their attachments, combining the multipart attachments, recovering missing parts, generating thumbnail previews for media files, building a sophisticated declarative forms module, etc."}
   {:when "2004 to the end of 2006"
    :what "Figured out I'd be better off doing freelance consulting and tackled a wide range of projects for my clients: wrote a fancy GTK UI in Python for a self order payment kiosk provider, implemented a monstrous intranet portal in PHP, developed a non-trivial <abbr title=\"Virtual File System\">VFS</abbr> driver for Windows, and built a full-blown DNS server in C++ from scratch. If it could be <abbr title=\"A computer hacker is a computer expert who uses their technical knowledge to achieve a goal or overcome an obstacle, within a computerized system by non-standard means.\">hacked</abbr>, I'd be onto it. Those were fun times."}
   {:when "January 2007"
    :what "Landed a job at [Acronis](https://www.acronis.com/en-us/), moved to Moscow, and dived into building a .Net/COM bridge to the company's proprietary distributed object framework used in their enterprise products. The framework had <abbr title=\"Remote Method Invocations\">RMI</abbr> interfaces defined in XML and spoke some elaborated binary protocol over the wire. To tie it all together I eventually provided some .Net runtime written in C# and a set of involved XSLT transformations to generate the bridge."}])
