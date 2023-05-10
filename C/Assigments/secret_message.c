/*
Compile: make secret_message
Run: ./secret_message
*/

#include "base.h"

/* 
   Erstellt aus einer Eingabe einen entschlüsselten String
   @param s verschlüsselter String
   @return entschlüsselter String
*/
String decode(String s) {
	String d = s_copy(s);
	for (int i=0; i < s_length(d); i++) {
		char letter = s_get(d, i);
        if (letter >= 'A' && letter <= 'Z') {
			letter = 'A' + 'Z' - letter;
		    s_set(d, i, letter); 
		} else if (letter >= 'a' && letter <= 'z') {
			letter = 'a' + 'z' - letter;
			s_set(d, i, letter);
		}			 
	}
	return d;
}

//Testet die Funktion decode
void decode_test() {
	test_equal_s(decode("B"), "Y");
	test_equal_s(decode("Z"), "A");
	test_equal_s(decode("a"), "z");
	test_equal_s(decode("T"), "G");
	test_equal_s(decode("ZA"), "AZ");
	test_equal_s(decode("GFeJmN"), "TUvQnM");
}

/*
   Erstellt aus einer Eingabe einen verschlüsselten String
   @param s entschlüsselter String
   @return verschlüsselter String
*/
String encode(String s) {
	s = decode(s);
	return s;
}

//Testet die Funktion encode
void encode_test() {
	test_equal_s(encode("B"), "Y");
	test_equal_s(encode("Z"), "A");
	test_equal_s(encode("a"), "z");
	test_equal_s(encode("T"), "G");
	test_equal_s(encode("ZA"), "AZ");
	test_equal_s(encode("GFeJmN"), "TUvQnM");
}

int main(void){
	//todo: Decode this message to get a hint for part c) + d)
	String secret_message = "Kiltiznnrvivm 1 nzxsg Hkzhh. Wrvh rhg pvrmv Dviyfmt ufvi vgdzrtv Kilwfpgv zfu wvn Yrow. Grkk: Wrv Olvhfmt ufvi wzh vmxlwrvivm rhg tzma vrmuzxs fmw kzhhg rm vrmv Avrov.";
	printsln(decode(secret_message));
	printsln(encode(decode(secret_message)));
	decode_test();
	encode_test();
	return 0;
}

