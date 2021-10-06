(ns subbotin-web.data)

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

(def timeline
  [{:when "May 14th, 1981"
    :what "Respawned into the amazing world at [56.01722131572176, 92.79474952035382](https://goo.gl/maps/7yLr2JbN3vidXTgn8)."}
   {:when "1987"
    :what "Discovered computer games in a local arcade. There were several Ataris and I and my friends spent many hours there playing [Fort Apocalypse](https://en.wikipedia.org/wiki/Fort_Apocalypse)."}
   {:when "September 1992"
    :what "Started learning Basic on a Soviet-era [UKNC-0511](https://en.wikipedia.org/wiki/UKNC)."}
   {:when "December 1992"
    :what "Persuaded my mother to buy me an [Electronika MS 1502](https://ru.wikipedia.org/wiki/Электроника_МС_1502). Unfortunately there was no way to connect it to our old Soviet black-and-white Rassvet 307-1 TV set. Still I managed to write some simple music-generating programs on it, all without being able to see a single character of the Basic code."}
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
    :what "Spent three weeks in a computer summer camp for kids mesmerized by an [SGI Indigo<sup>2</sup>](https://en.wikipedia.org/wiki/SGI_Indigo²_and_Challenge_M) and [IRIX](https://en.wikipedia.org/wiki/IRIX). Learnt what `ps ax` meant, how to `kill -9` stuck processes, and how to free disk space by removing core dumps from crashed software."}
   {:when "March 1996"
    :what "Learnt enough HTML to launch my own homepage on [GeoCities](https://en.wikipedia.org/wiki/Yahoo!_GeoCities). It had all of the hype bits included: a frameset, blinking text, and animated GIFs."}
   {:when "April 1996"
    :what "Discovered [Watcom C](https://en.wikipedia.org/wiki/Watcom_C/C%2B%2B) and the wonderful possibilities provided by [the DOS/4GW DOS extender](https://en.wikipedia.org/wiki/DOS/4G)."}
   {:when "February 1998"
    :what "My uncle gave me my first PC—a 60Mhz Pentium with 4MB RAM and a 512MB HDD—and a HP LaserJet printer. He hadn't given me a monitor though and I was too poor to buy one. So for a while the Prt Scrn key became my good friend."}
   {:when "April 1998"
    :what "Wrote [Boa Vision](https://archive.org/details/boa_20201231)—a snake game for my girlfriend. All of the images were encoded in my own [RLE](https://en.wikipedia.org/wiki/Run-length_encoding)-based format and the game sported a cool 8-bit tracker soundtrack and effects."}
   {:when "August 15th, 1998"
    :what "Just two days before [the Russian Rouble collapsed](https://en.wikipedia.org/wiki/1998_Russian_financial_crisis) my mother bought me a 15-inch LG Flatron monitor. Had it been two days later, we wouldn't be able to afford one."}
   {:when "End of August 1998"
    :what "Bought the 1st edition of the Java in a Nutshell book and fell in love with the language and the Write-Once-Run-Everywhere idea."}
   {:when "September 1998"
    :what "Installed Slackware Linux from a CD obtained from a friend."}
   {:when "Beginning of October 1998"
    :what "A friend happened to rent an apartment without a phone line and lent me his modem. After spending some time on the local BBSes I managed to become a point in FidoNet. 2:5090/47.41 was the address. "}
   {:when "Near the end of October 1998"
    :what "Got my first job as a web master. Occupied myself with all things Perl."}
   {:when "1999"
    :what "My obsession with Java continued. I developed an app to build educational presentations. The UI was close to the Visual Basic suite with all the drag'n'drop mechanics and widgets, and the resulting presentations were serialized into standalone player JARs that could run on both Windows and Linux. The project was awarded [the 3rd Place Award at the Soft-Parade student software contest](https://www.soft-parade.ru/1999)."}
   {:when "September 22nd, 2000"
    :what "Became a proper FidoNet node, 2:5090/114. The station answered calls 24×7 and my mother had to put our home phone into the silent mode."}
   {:when "March 2001"
    :what "Landed a web master job at a local advertising agency. ASP, PHP, [Parser](https://www.parser.ru/en/), MSSQL, PostgreSQL, and a ton of other technologies were together in daily use and I had to figure out all of them."}
   {:when "May 15th, 2002"
    :what "The company I was working for was a kind of ISP and they were desperetely in need of a billing solution for their clients, which I ended up developing full-time on top of Linux, iptables, [NeTraMet](https://www.caida.org/tools/measurement/netramet/), and PostgreSQL. The result even gained me [the 1st Place Award at the Soft-Parade student software contest](https://www.soft-parade.ru/node/362)."}
   {:when "February 2003"
    :what "Got hired by a team working on a binary Usenet groups aggregator service. I implemented lots of the backend and frontend mostly in Perl with occasional sprinkles of C: fetching of news articles and their attachments, combining multipart attachments, recovering missing parts, generating thumbnail previews for media files, building a sophisticated declarative forms module, etc."}
   {:when "2004 to the end of 2006"
    :what "Figured out I'd be better off doing freelance consulting and tackled a wide range of projects for my clients: wrote a sleek GTK UI in Python for a self order payment kiosk provider, implemented a monstrous intranet portal in PHP, developed a non-trivial <abbr title=\"Virtual File System\">VFS</abbr> driver for Windows, and built a full-blown DNS server in C++ from scratch. If it could be <abbr title=\"A computer hacker is a computer expert who uses their technical knowledge to achieve a goal or overcome an obstacle, within a computerized system by non-standard means.\">hacked</abbr>, I'd be onto it. Those were fun times."}
   {:when "January 2007"
    :what "Landed a job at [Acronis](https://www.acronis.com/en-us/), moved to Moscow, and dived into building a .Net/COM bridge to the company's proprietary distributed object framework used in their enterprise products. The framework had <abbr title=\"Remote Method Invocations\">RMI</abbr> interfaces defined in XML and spoke some elaborated binary protocol over the wire. To tie it all in together I eventually provided some .Net runtime written in C# and a set of involved XSLT transformations to generate the bridge."}
   {:when "February 2007"
    :what "Obtained a copy of Cocoa Programming for Mac OS X by Aaron Hillegass and managed to run OS X in a virtual machine on a PC to play with Xcode."}
   {:when "May 26th, 2007"
    :what "Bought my first Mac."}
   {:when "July 2008"
    :what "Signed up and got accepted to the iOS Developer Program."}
   {:when "February 4th, 2009"
    :what "Ditched the enterprise software world and jumped on the iOS software development bandwagon full-time."}
   {:when "August 2009 to mid-2011"
    :what "Joined a startup as the main developer on an international crew and spent countless hours coding in every imaginable cafe, airport, hotel, and coworking space in between Moscow, Miami, San Francisco, and New York."}
   {:when "May 23rd, 2011"
    :what "[Presented](https://techcrunch.com/2011/05/23/karizma-lets-you-video-chat-with-people-around-you-whether-you-know-them-or-not/) the Karizma video messenger app at the Startup Battlefield at the TechCrunch Disrupt conference in New York, USA. We had a great technology stack under the <nobr>hood—</nobr>[Jingle](https://bit.ly/2Liv67w), [VP8](https://en.wikipedia.org/wiki/VP8), [ICE](https://en.wikipedia.org/wiki/Interactive_Connectivity_Establishment), Mongo DB and <nobr>Redis—</nobr>but seriously lacked in user acquisition skills. The app failed to solve the chicken and the egg problem and failed to raise any funds. Shortly after, I burnt out and moved back to Moscow."}
   {:when "August 2011"
    :what "An acquaintance on Twitter led me to signing a full-time contract with Yandex. For the next couple of years I became a keen software engineer on the [Yandex.Maps](https://apps.apple.com/app/yandex-maps/id313877526) and the [Yandex.Metrica](https://metrica.yandex.com/) iOS teams. Think Google Maps for iOS and Google Analytics for iOS&#133; but in Russia."}
   {:when "November 2013"
    :what "Living on the coast had always been a dream for me. Moscow on the other hand had no coast whatsoever and working remotely was not an option at Yandex. I somehow stumbled on a recruiting pitch by someone from [Songsterr](https://songsterr.com/), got sold on it, and joined the team."}
   {:when "February 9th, 2014 at 12:40pm AEST"
    :what "My flight landed in Sydney. Australia became my new home."}
   {:when "March 2014 to the end of 2017"
    :what "Had opportunities to come up with solutions to several greenfield projects and became thoroughly familiar with many technologies of the modern web and iOS development—Angular, React and React Native, Terraform, Docker, Kubernetes, Rancher, EC2, ECR, TypeScript, Swift, and everything in between. Name any and I probably know a thing or two about it and how to use it effectively and, what's arguably more important, when not to use it."}
   {:when "2018"
    :what "Somewhere along the ride I stumbled upon [Clojure](https://clojure.org/about/rationale) and became a fan of Rich Hickey. The language and its ecosystem quickly became the harbour at which I spent most of my spare time. I absorbed knowledge of ClojureScript, Datomic, Reagent, and Re-frame. And then there was Emacs."}
   {:when "July 2020"
    :what "Laid my hands on [an HHKB keyboard](https://happyhackingkb.com/), set it in [Sonshi style](https://medium.com/lim-less-is-more/sonshi-style-a-style-of-putting-keyboard-on-laptop-67f0a825a53c), learnt Dvorak, and started typing like there was no tomorrow."}])
