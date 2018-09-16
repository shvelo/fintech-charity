package me.pirrate.fintechcharity

import me.pirrate.fintechcharity.api.models.Beneficiary

object PrototypeHelpers {
    fun getBeneficiaries(): List<Beneficiary> {
        val beneficiaryList = ArrayList<Beneficiary>()

        beneficiaryList.add(Beneficiary(
                "SOS ბავშვთა სოფელი",
                "Charity 1 description",
                "GE11DUMMY00000000GEL",
                "https://scontent-sof1-1.xx.fbcdn.net/v/t1.0-9/26907130_1707874392606539_4641843153855796127_n.jpg?_nc_cat=0&oh=d53d89795ff64f87d5a0ebe0d7fe1392&oe=5C227BD3",
                "http://sos-childrensvillages.ge/"
        ))

        beneficiaryList.add(Beneficiary(
                "სოლიდარობის ფონდი",
                "Charity 2 description",
                "GE11DUMMY00000000GEL",
                "https://scontent-sof1-1.xx.fbcdn.net/v/t1.0-9/24177172_1663963917011743_2813654673064890894_n.png?_nc_cat=0&oh=f287252dac6847a8b6140b2d031f49b0&oe=5C1A4DC5",
                "http://faq.gov.ge/"
        ))

        beneficiaryList.add(Beneficiary(
                "დიმიტრი ცინცაძის სახელობის ფონდი",
                "Charity 3 description",
                "GE11DUMMY00000000GEL",
                "https://scontent-sof1-1.xx.fbcdn.net/v/t1.0-9/1457765_722191011228354_1828495477774684273_n.jpg?_nc_cat=0&oh=c404f707d5c6d6abaedf6a2027b25532&oe=5C21A875",
                "www.media4life.ge/"
        ))

        beneficiaryList.add(Beneficiary(
                "ერთად ვიზრუნოთ და გადავარჩინოთ",
                "Charity 3 description",
                "GE11DUMMY00000000GEL",
                "https://scontent-sof1-1.xx.fbcdn.net/v/t1.0-9/12733373_1668590453383575_2006568122220466989_n.jpg?_nc_cat=0&oh=078fb55625f93989c5aeaf283d1a76ff&oe=5C15FCAE",
                "https://www.facebook.com/ertadvizrunotdagadavarchinot/"
        ))

        beneficiaryList.add(Beneficiary(
                "პირველი ნაბიჯი",
                "Charity 3 description",
                "GE11DUMMY00000000GEL",
                "https://scontent-sof1-1.xx.fbcdn.net/v/t1.0-9/15241839_1189608077804304_2019014193708410431_n.png?_nc_cat=0&oh=20a3be5903aa72479959fc60000af12b&oe=5C21A836",
                "http://firststep.ge/ge"
        ))

        return beneficiaryList
    }
}