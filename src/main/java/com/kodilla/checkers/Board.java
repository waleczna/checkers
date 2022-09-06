package com.kodilla.checkers;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<BoardRow> rows = new ArrayList<>();
    private FigureColor whoseMove = FigureColor.WHITE;

    public Board() {
        for (int n = 0; n < 8; n++) {
            rows.add(new BoardRow());
        }
    }
    public FigureColor getWhoseMove() {
        return whoseMove;
    }
    public Figure getFigure(int col, int row) {
        return rows.get(row).getCols().get(col);
    }

    public void setFigure(int col, int row, Figure figure) {
        rows.get(row).getCols().set(col, figure);
    }

    public boolean move(int col1, int row1, int col2, int row2) {
        //zaznaczone wiersze i prawy klik, refactor, extract method, ponizej w return zostaje tylko wywolanie metody pawnMove a nie sama metoda move
        //void zmienione na boolean, ta metoda z parametrami zwróci nam true jezeli ruch byl poprawny i dobrze wykonany a false jezeli byl zly
        if (getFigure(col1, row1) instanceof Pawn) //jezeli figura, na ktora klikam jest typu Pawn to wtedy
            return pawnMove(col1, row1, col2, row2); //pionek przesuwa sie z jednego pola na drugie
        else if (getFigure(col1, row1) instanceof Queen) //a w przesciwnym wypadku, jezeli figura jest damka
            return queenMove(col1, row1, col2, row2); //damka wykonuje ruch
        else //a jezeli nie pionek ani nie damka, to znaczy, ze jest to puste pole
            return false; //uznamy, ze jest to ruch niepoprawny, opisane jest to w warunku klasy Game, tu tylko dodatkowo, bo nie wiemy skad ta metoda bedzie wywolywana
    }

    private boolean queenMove(int col1, int row1, int col2, int row2) { //do opisania metoda damki, ktora bedzie miala nieco inna logike niz pionek
        boolean isHit = false; //zmienna bijaca zakladmay, ze false (czyli to nie jest bicie), bo jezeli to jest bicie to 38, 39, opisane nizej
        boolean result = true; //&& - pierwszy i drugi warunek musi byc true wtedy cale wyrazenie jest true
        result = result && istColorCorrect(whoseMove, getFigure(col1, row1).getColor()); //warunek czy poruszamy sie figura we wlasciwym kolorze isColorCorrect
        result = result && isMoveDiagonal(col1, row1, col2, row2); //warunek czy ruch odbywa sie po przekatnej
        result = result && isTargetFieldEmpty(col2, row2); //warunek czy pole, na ktore bedzie odbywal sie ruch jest puste
        result = result && isPositionInsideBoard(col2, row2); //warunek czy pozycja sie miesci w planszy
        if (isPathClear(col1, row1, col2, row2)) { //jezeli sciezka jest pusta to bedzie to zwykly ruch a w przeciwynym razie
            result = true; //ten warunek zawsze jest true
        } else { //w przeciwnym razie gdy figura przeciwnika znajdzie sie na przedostatnim miejscu, wtedy bedzie bicie
            result = result && isQueenHit(col1, row1, col2, row2);
            if (result) //jezeli to sie okazalo prawda
                isHit = true; //to isHit = prawda
        }
        if (result) { //jezeli rezultat jest poprawny to wykonujemy ruch
            Figure figure = getFigure(col1, row1); //the real move under above conditions
            setFigure(col2, row2, figure); //the real move under above conditions
            setFigure(col1, row1, new None()); //the real move under above conditions
            if (isHit) {
                remoweHittedFigure(col1, row1, col2, row2); //w tym miejscu zostanie usunieta figura, ktora zostanie zbita
            }
        }
        return result; //
    }

    private void remoweHittedFigure(int col1, int row1, int col2, int row2) { //ponizej liczymy kierunek w ktorym idziemy w pionie i w poziomie i obliczamy wspolrzedne
        int deltaCol = (col2 > col1) ? 1 : -1; //jezeli delta kol rosnie to idziemy w prawo 1 a jezeli maleje to w lewo -1
        int deltaRow = (row2 > row1) ? 1 : -1; //jw tylko dla rzedow
        int col = col1 + (Math.abs(col1 - col2) - 1) * deltaCol; //liczymy wspolrzedne
        int row = row1 + (Math.abs(row1 - row2) - 1) * deltaRow; //delta Row to wartosc +1 lub -1 w zaleznosci od tego czy szlismy w gore czy w dol
        setFigure(col, row, new None()); //wstawiamy tam pusta figure
    }

    private boolean isQueenHit(int col1, int row1, int col2, int row2) { //ruch dla bicia damka
        int deltaCol = (col2 > col1) ? 1 : -1; //jezeli delta kol rosnie to idziemy w prawo 1 a jezeli maleje to w lewo -1
        int deltaRow = (row2 > row1) ? 1 : -1; //jw tylko dla rzedow
        //w petli przejdziemy przez wszystkie pola i sprawdzimy czy sa wszytkie puste jn
        boolean result = true; //zakladamy, ze sciezka jest pusta
        for (int n = 1; n <= Math.abs(col1 - col2) - 2; n++) { //-2 za nawiasem oznacza dwa miejsca, ktore musi pokonac damka jesli nia jest ???
            //1 w nawiasie oznacza miejsce ale nastepne, nie to, na ktorym stoimy, czyli n oznacza odleglosc od miejsca poczatkowego
            //wartosc bezwzgledna col1 - col2
            int col = col1 + n * deltaCol; //delta col albo +1 albo -1 albo w prawo albo w lewo
            int row = row1 + n * deltaRow; //jw
            result = result && (getFigure(col, row) instanceof None); //czy ta figura jest pusta figura czyli czy jest tu miejsce wolne na plaszy
        }

        int col = col1 + (Math.abs(col1 - col2) - 1) * deltaCol;
        int row = row1 + (Math.abs(row1 - row2) - 1) * deltaRow; //delta Row to wartosc +1 lub -1 w zaleznosci od tego czy szlismy w gore czy w dol
        result = result && isOppositeFigure(whoseMove, col, row);
        return result;
    }

    private boolean isOppositeFigure(FigureColor color, int col, int row) {
        return getFigure(col, row).getColor() == oppositeColor(color);
    }
    //a jezeli nie mamy sciezki pustej to moze sie okazac ze ostatnie miejsce jest zajete i to przez przeciwnika
    //dlatego musimy sprawdzic czy przedostatnie miejsce jest zajete przez przeciwnika i wtedy go zbic zajmujac miejsce tuz za nim
    private boolean isPathClear(int col1, int row1, int col2, int row2) { //sprawdzanie po kolei czy scieżka jest pusta dla damki podczas zwyklego ruchu
        int deltaCol = (col2 > col1) ? 1 : -1; //jezeli delta kol rosnie to idziemy w prawo 1 a jezeli maleje to w lewo -1
        int deltaRow = (row2 > row1) ? 1 : -1; //jw tylko dla rzedow
        //w petli przejdziemy przez wszystkie pola i sprawdzimy czy sa wszytkie puste jn
        boolean result = true; //zakladamy ze sciezka jest pusta
        for (int n = 1; n <= Math.abs(col1 - col2) - 1; n++) { //-1 za nawiasem oznacza, że docelowe pole nas nie interesuje, bo osobno je sprawdzamy
            //1 oznacza miesjce ale nastepne, nie to, na ktorym stoimy, czyli n oznacza odleglosc od miejsca poczatkowego
            //wartosc bezwzgledna col1 - col2
            int col = col1 + n * deltaCol; //delta col albo +1 albo -1 albo w prawo albo w lewo
            int row = row1 + n * deltaRow; //jw
            result = result && (getFigure(col, row) instanceof None); //czy ta figura jest pusta figura czyli czy jest tu miejsce wolne na plaszy

        }
        return result; //na koncu zwracamy rezultat boolean

    }

    private boolean pawnMove(int col1, int row1, int col2, int row2) {
        boolean isHit = false; //zmienna bijaca zakladmay, ze false (czyli to nie jest bicie), bo jezeli to jest bicie to 38, 39
        boolean result = true; //&& - pierwszy i drugi warunek musi byc true
        result = result && istColorCorrect(whoseMove, getFigure(col1, row1).getColor()); //warunek czy poruszamy sie figura we wlasciwym kolorze isColorCorrect
        result = result && isMoveDiagonal(col1, row1, col2, row2); //warunek czy ruch odbywa sie po przekatnej
        result = result && isTargetFieldEmpty(col2, row2); //warunek czy pole, na ktore bedzie odbywal sie ruch jest puste
        result = result && isPositionInsideBoard(col2, row2); //warunek czy pozycja miesci sie w planszy
        if (result && isMoveByTwo(col1, col2)) { //jezeli ruch odbywa sie od kol1 do kol2 to byc moze bicie a w przeciwnym razie jn
            result = result && isOpponentBetween(oppositeColor(whoseMove), col1, row1, col2, row2);
            if (result)
                isHit = true;
        } else if (result) { //dodany warunek, bo gdy poszlismy pionkiem właściwego koloru to ok, jesli nie to nie ma sensu sprawdzac pozostalych warunkow
            result = result && isDirectionCorrect(whoseMove, row1, row2); //warunek czy poruszamy sie we wlasciwym kierunku
            result = result && isMoveByOne(col1, col2); //warunek czy ruch jest o jedno pole isMoveByOne
        }
        if (result) {
            Figure figure = getFigure(col1, row1); // -37 ???
            setFigure(col2, row2, figure); //the real move under above conditions - 38
            setFigure(col1, row1, new None()); // - 39 ???
            if (isHit) {
                remoweFigureBetween(col1, row1, col2, row2);  //jezeli ruszymy ta figure to jeszcze musimy usunac ta ktora zbilismy
            }
            if (row2 == 0 || row2 == 7) { //jezeli docelowy wiersz row2, do ktorego doszlismy jest rowny 0 lub jest rowny 7 tzn., ze doszlismy do krawedzi
                switchPawnToQueen(col2, row2); //wtedy przelaczamy pionka na damke na polu docelowym
            }
            whoseMove = oppositeColor(whoseMove);
        }
        return result;
    }

    private boolean isPositionInsideBoard(int col, int row) { //nie oznakowane kolumny i wiersze
        return col >= 0 && col < 8 && row >= 0 && row < 8;
    }

    private void switchPawnToQueen(int col, int row) { //linia i rzad nie sa oznaczone cyframi
        FigureColor color = getFigure(col, row).getColor(); //tu dowiadujemy sie jakiego koloru jest ta figura
        setFigure(col, row, new Queen(color)); //wstawia damke w tym miejscu zamiast pionka teraz tworzymy metode ruchu damki
    }

    private void remoweFigureBetween(int col1, int row1, int col2, int row2) {
        int deltaCol = (col2 > col1) ? 1 : -1; //jezeli delta kol rosnie to idziemy w prawo 1 a jezeli maleje to w lewo -1
        int deltaRow = (row2 > row1) ? 1 : -1; //jw tylko dla rzedow
        setFigure(col1 + deltaCol, row1 + deltaRow, new None());
    }

    private boolean isOpponentBetween(FigureColor expectedFigureColor, int col1, int row1, int col2, int row2) {
        //wspolrzedne punktu nas interesujacego to wspolrzedne punktu poczatkowego plus delta row i delta col (albo +1 albo -1 w zaleznosci od kierunku)
        int deltaCol = (col2 > col1) ? 1 : -1; //jezeli delta kol rosnie to idziemy w prawo 1 a jezeli maleje to w lewo -1
        int deltaRow = (row2 > row1) ? 1 : -1; //jw tylko dla rzedow
        return getFigure(col1 + deltaCol, row1 + deltaRow).getColor() == expectedFigureColor; //zwroc pobrana figure w kolorze oczekiwanym czyli przeciwnika
        //jezeli tak i parametry z metody move sa spelnione to wiemy, ze jest bicie - opisane w mniej wiecej liniach 26 - 34
        //return false; //propo IJ
    }

    private boolean isMoveByTwo(int col1, int col2) {
        return Math.abs(col1 - col2) == 2; //ma zwrócić wartość tego wyrażenia, które jest równe 2, jest to wynik roznicy miedzy kolumnami
    }

    private boolean isTargetFieldEmpty(int col2, int row2) {
        return getFigure(col2, row2) instanceof None; //do tego, zeby sprawdzic klase obiektu, ktory zwrociła nam metoda, uzywamy instanceof, tu czy jest klasy NONE
    }

    private boolean isMoveDiagonal(int col1, int row1, int col2, int row2) {
        return Math.abs(col1 - col2) == Math.abs(row1 - row2); //porownana roznica w wierszach oraz roznica w kolumnach daje zwrot ze ruch odbywa sie po przekatnej
    }

    private boolean isMoveByOne(int col1, int col2) {
        return Math.abs(col1 - col2) == 1;
    }

    private boolean isDirectionCorrect(FigureColor whoseMove, int row1, int row2) {
        if (whoseMove == FigureColor.WHITE)
            return row2 > row1;
        else
            return row1 > row2;
    }

    private boolean istColorCorrect(FigureColor whoseMove, FigureColor figureColor) {
        return whoseMove == figureColor; //
    }

    private FigureColor oppositeColor(FigureColor color) {
        return (color == FigureColor.WHITE) ? FigureColor.BLACK : FigureColor.WHITE; //
    }

    @Override
    public String toString() {
        String s = "|--|--|--|--|--|--|--|--|\n";
        for (int row = 0; row < 8; row++) {
            s += rows.get(row).toString();
        }
        s += "|--|--|--|--|--|--|--|--|\n";
        return s;
    }

    public void initBoard() {
        for (int col = 0; col < 8; col += 2)
            setFigure(col, 0, new Pawn(FigureColor.WHITE));
        for (int col = 1; col < 8; col += 2)
            setFigure(col, 1, new Pawn(FigureColor.WHITE));
        for (int col = 0; col < 8; col += 2)
            setFigure(col, 6, new Pawn(FigureColor.BLACK));
        for (int col = 1; col < 8; col += 2)
            setFigure(col, 7, new Pawn(FigureColor.BLACK));
    }
}

