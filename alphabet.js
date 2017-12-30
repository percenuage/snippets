console.log('---- START ALPHABET -----');

var alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
var ALPHABET_SIZE = alphabet.length;

function getIndexFromLetter(letters) {
    var index = 0;
    for (var i = 0; i < letters.length; i++) {
        index += Math.pow(ALPHABET_SIZE, letters.length - 1 - i) * (1 + alphabet.indexOf(letters[i]));
    }
    index--;
    console.log(letters, index);
}

function getLetterFromIndex(index) {
    var letters = index + " ";
    var pow = 0, res = 0;
    while (index >= res) {
        pow++;
        res += Math.pow(ALPHABET_SIZE, pow);
    }
    var mod, n;
    while (pow > 0) {
        pow--;
        mod = Math.pow(ALPHABET_SIZE, pow);
        res = index % mod;
        n = (index - res) / mod;
        if (index >= ALPHABET_SIZE) n--;
        letters += alphabet[n];
        index = res;
    }
    console.log(letters);
}

getLetterFromIndex(18251);
getLetterFromIndex(18252);
getLetterFromIndex(18276);
getLetterFromIndex(18277);
getLetterFromIndex(18278);

function showColumnExcelLabel() {

    var letters = [], n = 0;

    var showLetters = function (numbersArray, index) {
        var string = numbersArray.map(function(value) {
            return alphabet[value];
        });
        console.log(index, string.reverse().join(''));
    };

    for (var i = 0; i <= INDEX_MAX_TWO_LETTER; i++) {
        n = 0;

        while (n != -1) {
            if (n > letters.length - 1) {
                letters.push(0);
            } else {
                letters[n]++;
            }
            if (letters[n] >= alphabet.length) {
                letters[n] = 0;
                n++;
            } else {
                n = -1;
            }
        }
        showLetters(letters, i);
    }
}
