class AcademicArena extends Program {



    char EMPTY = ' ';
    Pixel EMPTYPIXEL = newPixelEmpty();
    String RESSOURCESDIR = "ressources";
    String LOGOPARTA = RESSOURCESDIR + "/" + "academic.txt";
    String LOGOPARTB = RESSOURCESDIR + "/" + "arena.txt";
    String PLAYER = RESSOURCESDIR + "/" + "player.txt";
    String PLAYER2 = RESSOURCESDIR + "/" + "playertest.txt";
    String PLAYER3 = RESSOURCESDIR + "/" + "player3.txt";
    String choosecharacter = RESSOURCESDIR + "/" + "choose.txt";
    String NUMBERDIR = RESSOURCESDIR + "/" + "numbers";
    String MOBDIR = RESSOURCESDIR + "/" + "mobs";
    String PLAYERFILE = RESSOURCESDIR + "/" + "players.csv";

    char[] list_EMPTY = new char[]{' ', ' ', ' ', ' ', ' ',' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '　', '⠀'};
    Mob[] listMob ;

    void loadMob() {
        extensions.CSVFile f = loadCSV(RESSOURCESDIR + "/" + "mob.csv");
        listMob = new Mob[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            listMob[i-1] = newMob(StringToInt(getCell(f, i, 0)), StringToInt(getCell(f, i, 1)), getOperation(getCell(f, i, 2)), loadASCII(MOBDIR + "/" + getCell(f, i, 3), getANSI_COLOR(getCell(f, i, 4))), 0, 0);
        }

    }

    void refresh(Screen main) {
        drawHorizontalLine(main, 0, ANSI_TEXT_DEFAULT_COLOR);
        drawHorizontalLine(main, main.height-1, ANSI_TEXT_DEFAULT_COLOR);
        drawVerticalLine(main, 0, ANSI_TEXT_DEFAULT_COLOR);
        drawVerticalLine(main, main.width-1, ANSI_TEXT_DEFAULT_COLOR);
        println(toString(main));
    }

    String getletterPath(char c) {
        if (c == ' ') {
            return RESSOURCESDIR + "/letters/" + "space" + ".txt";
        }
        return RESSOURCESDIR + "/letters/" + c + ".txt"; 
    }

    Operation getOperation(String operation) {
        if (equals(operation, "addition")) {
            return Operation.ADDITION;
        }
        else if (equals(operation, "multiplication")) {
            return Operation.MULTIPLICATION;
        }
        else if (equals(operation, "division")) {
            return Operation.DIVISION;
        }
        else if (equals(operation, "soustraction")) {
            return Operation.SOUSTRACTION;
        }
        else {
            return Operation.ADDITION;
        }
        
    }

    // boolean challenge(int min, int max) {
    //     int nb1;
    //     int nb2;
    //     if (min < 0) {
    //         nb1 = random(-min, max+(-min)) + min;
    //         nb2 = random(-min, max+(-min)) + min;
    //     }
    //     else {
    //         nb1 = random(min, max);
    //         nb2 = random(min, max);
    //     }

    //     int response ;
    //     print("Reponse : ");
    //     response = readInt();
    //     return response == nb1 + nb2;
    // }    

    String getANSI_COLOR(String color) {
        String result = "";
        switch (color) {
            case "black":
                result = ANSI_BLACK;
                break;
            case "red":
                result = ANSI_RED;
                break;
            case "green":
                result = ANSI_GREEN;
                break;
            case "yellow":
                result = ANSI_YELLOW;
                break;
            case "blue":
                result = ANSI_BLUE;
                break;
            case "purple":
                result = ANSI_PURPLE;
                break;
            case "cyan":
                result = ANSI_CYAN;
                break;
            case "white":
                result = ANSI_WHITE;
                break;
            default:
                result = ANSI_TEXT_DEFAULT_COLOR;
                break;
        }
        return result;
    }

    boolean isEmpty(char c) {
        int cpt = 0;
        boolean result = false;
        while (cpt < length(list_EMPTY) && !result) {
            result = c == list_EMPTY[cpt];
            cpt = cpt + 1;
        }
        return result;
    }

    Pixel newPixel(char c, String color) {
        Pixel p = new Pixel();
        p.c = color + c;
        return p;
    }

    int StringToInt(String text) {
        int result = 0;
        int cpt = 0;
        while (cpt < length(text)) {
            result = result * 10 + charToInt(charAt(text, cpt));
            cpt = cpt + 1;
        }
        return result;
    }

    // int charToInt(char c) {
    //     return c - '0';
    // }

    Pixel newPixelEmpty() {
        Pixel p = new Pixel();
        p.c = ""+EMPTY;
        return p;
    }

    Screen newScreen(int height, int width) {
        Screen sr = new Screen();
        sr.height = height;
        sr.width = width;
        sr.screen = new Pixel[height][width];
        for (int i = 0; i < length(sr.screen, 1); i++) {
            for (int j = 0; j < length(sr.screen, 2); j++) {
                sr.screen[i][j] = newPixelEmpty();
            }
        }
        return sr;
    }

    String toString(Pixel pixel) {
        return pixel.c;
    }

    String toString(Mob m) {
        return m.hp + " " + m.atk + " " + m.faiblesse + " " + m.visuel + " " + m.posx + " " + m.posy;
    }

    boolean equals(Pixel p1, Pixel p2) {
        return charAt(p1.c, length(p1.c) - 1) ==  charAt(p2.c, length(p2.c) - 1);
    }

    void testEqualsPixel() {

    }

    String toString(Screen screen) {
        String result = "";
        for (int i = 0; i < length(screen.screen, 1); i++) {

            for (int j = 0; j < length(screen.screen, 2); j++) {
                result = result + toString(screen.screen[i][j]);
            }
            result = result + '\n' + ANSI_TEXT_DEFAULT_COLOR;
        }
        return result + ANSI_TEXT_DEFAULT_COLOR;
    }

    void testToStringScreen() {
        Screen sr = newScreen(2, 2);
        sr.screen[0][0] = newPixel('H', "");
        sr.screen[1][1] = newPixel('Y', "");
        assertEquals(toString(sr.screen[0][0]) + toString(sr.screen[0][1]) + "\n" + ANSI_TEXT_DEFAULT_COLOR + toString(sr.screen[1][0]) + toString(sr.screen[1][1]) + "\n" + ANSI_TEXT_DEFAULT_COLOR + ANSI_TEXT_DEFAULT_COLOR  , toString(sr));
    }

    Mob newMob(int hp, int atk, Operation faiblesse, Screen visuel, int posx, int posy) {
        Mob m = new Mob();
        m.hp = hp;
        m.initialHp = hp;
        m.dead = false;
        m.atk = atk;
        m.faiblesse = faiblesse;
        m.visuel = visuel;
        m.posx = posx;
        m.posy = posy;
        return m;
    }

    void testnewMob() {
        int hp = 10;
        int atk = 12;
        Operation faiblesse = Operation.ADDITION;
        Screen visuel = newScreen(10, 10);
        int posx = 0;
        int posy = 10;
        Mob m = newMob(hp, atk, faiblesse, visuel, posx, posy)  ;
        assertEquals(hp, m.hp);
        assertEquals(atk, m.atk);
        assertEquals(visuel, m.visuel);
        assertEquals(posx, m.posx);
        assertEquals(faiblesse, m.faiblesse);
        assertEquals(posy, m.posy);
    }

    Mob copyMob(Mob m) {
        Mob result = newMob(m.hp, m.atk, m.faiblesse, m.visuel, m.posx, m.posy);
        return result;
    }

    String fileAsString(String filePath) {
        extensions.File f = newFile(filePath);
        String result = "";
        if (ready(f)) {
            String line = "f";
            while (line != null) {
                line = readLine(f);
                result = result + '\n' + line;
            }
        }
        return substring(result, 1, length(result));

    }

    int count(String text, char car) {
        int result = 0;
        for (int i = 0; i < length(text); i++) {
            if (charAt(text, i) == car) {
                result = result + 1;
            }
        }
        return result;
    }

    int IndexFirst(String text, char target) {
        int result = -1;
        int cpt = 0;
        while (cpt < length(text) && result == -1) {
            if (charAt(text, cpt) == target) {
                result = cpt;
            }
            cpt = cpt + 1;
        }
        return result;
    }

    Screen loadASCII(String file, String color) {
        String text = fileAsString(file);
        int height = count(text, '\n');
        int width = length(substring(text, 0, IndexFirst(text, '\n')));
        Pixel[][] screen = new Pixel[height][width];
        int cptW = 0;
        char current = '\0';
        int cpt = 0;
        for (int i = 0; i < height; i++) {
            while ( current != '\n' && cpt < length(text) && cptW < width) {
                current = charAt(text, cpt);
                if (isEmpty(current)) {
                    current = ' ';
                }
                screen[i][cptW] = newPixel(current, color);
                cpt = cpt + 1;
                cptW = cptW + 1;
            }
            screen[i][cptW-1].c = screen[i][cptW-1].c + ANSI_TEXT_DEFAULT_COLOR;
            cptW = 0;
            cpt = cpt + 1;
        }
        Screen result = newScreen(height, width);
        result.screen = screen;
        return result;
    }


    void applyPatch(Screen main, Screen patch, int h, int w) {
        if ((w < main.width) && h < main.height) {
            int cptH_main = h;
            int cptH_patch = 0;
            int cptW_main = w;
            int cptW_patch = 0;
            while (cptH_patch < length(patch.screen, 1) && cptH_main < length(main.screen, 1)) {
                while (cptW_patch < length(patch.screen, 2) && cptW_main < length(main.screen, 2)) {
                    if (cptH_main >= 0 && cptW_main >= 0) {
                        main.screen[cptH_main][cptW_main] = patch.screen[cptH_patch][cptW_patch];
                    }
                    cptW_main = cptW_main + 1;
                    cptW_patch = cptW_patch + 1;
                }
                cptH_main = cptH_main + 1;
                cptH_patch = cptH_patch + 1;
                cptW_main = w;
                cptW_patch = 0;
            }
            
        }

    }

    void applyPatch(Screen main, Screen patch, int h, int w, boolean eraseNonEmpty) {
        if ((w < main.width) && h < main.height) {
            int cptH_main = h;
            int cptH_patch = 0;
            int cptW_main = w;
            int cptW_patch = 0;
            while (cptH_patch < length(patch.screen, 1) && cptH_main < length(main.screen, 1)) {
                while (cptW_patch < length(patch.screen, 2) && cptW_main < length(main.screen, 2)) {
                    if (!eraseNonEmpty && !equals(patch.screen[cptH_patch][cptW_patch], EMPTYPIXEL))
                    if (cptH_main >= 0 && cptW_main >= 0) {
                        main.screen[cptH_main][cptW_main] = patch.screen[cptH_patch][cptW_patch];
                    }
                    cptW_main = cptW_main + 1;
                    cptW_patch = cptW_patch + 1;
                }
                cptH_main = cptH_main + 1;
                cptH_patch = cptH_patch + 1;
                cptW_main = w;
                cptW_patch = 0;
            }
            
        }

    }

    void removePatch(Screen main, Screen patch, int h, int w) {
        Screen n = newScreen(patch.height, patch.width);
        for (int i = 0; i < length(n.screen, 1); i++) {
            for (int j = 0; j < length(n.screen, 2); j++) {
                n.screen[i][j] = newPixel(EMPTY, ANSI_TEXT_DEFAULT_COLOR);
            }
        }
        applyPatch(main, n, h, w);
    }

    void removePatch(Screen main, Screen patch, int h, int w, boolean transistion) {
        Screen n = newScreen(1, patch.width);
        drawHorizontalLine(n, 0);
        for (int i = 0; i < patch.height+1; i++) {
            removePatch(patch, n, i-1, 0);
            applyPatch(patch, n, i, 0);
            applyPatch(main, patch, h, w);
            refresh(main);
        }
    }

    

    void moveRight(Screen main, Screen patch, int curH, int curW) {
        removePatch(main, patch, curH, curW);  
        applyPatch(main, patch, curH, curW+1);     
    }

    void moveLeft(Screen main, Screen patch, int curH, int curW) {
        removePatch(main, patch, curH, curW);  
        applyPatch(main, patch, curH, curW-1);     
    }

    void moveTop(Screen main, Screen patch, int curH, int curW) {
        removePatch(main, patch, curH, curW);  
        applyPatch(main, patch, curH - 1, curW);
    }

    void moveBottom(Screen main, Screen patch, int curH, int curW) {
        removePatch(main, patch, curH, curW);  
        applyPatch(main, patch, curH + 1, curW);     
    }


    void moveTo(Screen main, Screen patch, int curH, int curW, int targetH, int targetW, int speed) {
        if (targetH > curH) {
            while (curH != targetH) {
                moveBottom(main, patch, curH, curW);
                curH = curH + 1;
                if (curH % speed == 0) {
                    refresh(main);
                }
            }
        }
        else if (targetH < curH) {
            while (curH != targetH) {
                moveTop(main, patch, curH, curW);
                curH = curH - 1;
                if (curH % speed == 0) {
                    refresh(main);
                }
            }
        }
        if (targetW > curW) {
            while (curW != targetW) {
                moveRight(main, patch, curH, curW);
                curW = curW + 1;
                if (curW % speed == 0) {
                    refresh(main);
                }
            }
        }
        else if (targetW < curW) {
            while (curW != targetW) {
                moveLeft(main, patch, curH, curW);
                curW = curW - 1;
                if (curW % speed == 0) {
                    refresh(main);
                }
            }
        }
        refresh(main);
    }

    

    void drawVerticalLine(Screen main, int w, String color) {
        Screen line = newScreen(main.height, 1);
        for (int i = 1; i < line.height; i++) {
            line.screen[i][0] = newPixel('▐', color);
        }
        applyPatch(main, line, 0, w);
    }

    void drawVerticalLine(Screen main, int w) {
        Screen line = newScreen(main.height, 1);
        for (int i = 1; i < line.height; i++) {
            line.screen[i][0] = newPixel('▐', "");
        }
        applyPatch(main, line, 0, w);
    }

    void drawHorizontalLine(Screen main, int h, String color) {
        Screen line = newScreen(1, main.width);
        for (int i = 0; i < line.width; i++) {
            line.screen[0][i] = newPixel('▁', color);
        }
        applyPatch(main, line, h, 0);
    }

    void drawHorizontalLine(Screen main, int h) {
        Screen line = newScreen(1, main.width);
        for (int i = 0; i < line.width; i++) {
            line.screen[0][i] = newPixel('▁', "");
        }
        applyPatch(main, line, h, 0);
    }

    void afficherLogo(Screen main){
        Screen partA = loadASCII(LOGOPARTA, ANSI_RED);
        Screen partB = loadASCII(LOGOPARTB, ANSI_YELLOW);
        int posX_A = -150;
        int posy_A = 10;
        int posX_B = main.width - (posX_A +  125);
        int posy_B = 25;

        for (int i = posX_A; i < 20; i++) {
            posX_A = posX_A + 1;
            posX_B = posX_B - 1;
            moveRight(main, partA, posy_A, i);
            moveLeft(main, partB, posy_B, posX_B);
            refresh(main);
        } 

        print("             Press enter to start                ");
        readString();

        for (int i = 0; i < 50; i++) {
            moveTop(main, partA, posy_A - i, posX_A) ;
            moveBottom(main, partB, posy_B + i, posX_B);
            refresh(main);
        }


    }

    // void mainMenue(Screen main) {
    //     Screen choice = newScreen(25, 120);
    //     Screen player = newScreen(25, 114);
    //     Screen player_ASCII = loadASCII(PLAYER, ANSI_BLUE);
    //     applyPatch(player, player_ASCII, 2, 20);
    //     applyPatch(main, player, 25, 120, false);
    //     drawVerticalLine(main, 120);
    //     refresh(main);

    // }



    Screen getNumber(int number, String color) {
        if (number < 1000 && number > 0) {
            Screen[] list_nb = new Screen[3];
            for (int i = 0; i < length(list_nb); i++) {
                list_nb[i] = newScreen(0, 0);
            }
            int cpt = 0;
            while (number > 0) {
                int r = number % 10;
                number = number / 10;
                list_nb[cpt] = loadASCII(NUMBERDIR + "/" + r + ".txt", color);
                cpt = cpt + 1;
            }
            Screen number_ASCII = newScreen(list_nb[0].height, list_nb[0].width + list_nb[1].width + list_nb[2].width);
            int withDec = 0;
            for (int i = length(list_nb) - 1; i >= 0; i--) {
                applyPatch(number_ASCII, list_nb[i], 0, withDec);
                withDec = withDec + list_nb[i].width;
            }


            return number_ASCII;        }
        else {
            Screen number_ASCII = loadASCII(NUMBERDIR + "/" + "0" + ".txt", color);
            return number_ASCII;
        }
    }


    

    Screen chooseCharacter(Screen main) {
        Screen choice = newScreen((main.height/4) * 3, main.width);
        Screen prompt = newScreen(main.height/4, main.width);
        int curH = 3;
        int witdh_last = 0;
        int r;
        int hchoice = 0;
        int wchoice = 0;
        int midleh;
        int midlew;

        Screen[] list_perso = new Screen[4];
        list_perso[0] = loadASCII(PLAYER, ANSI_RED);
        list_perso[1] = loadASCII(PLAYER2, ANSI_GREEN);
        list_perso[2] = loadASCII(PLAYER3, ANSI_YELLOW);
        list_perso[3] = loadASCII(PLAYER, ANSI_BLUE);


        Screen choose_ASCII = loadASCII(choosecharacter, ANSI_WHITE);
        applyPatch(choice, genText("Choisi ton personnage", ""), curH, choice.width/2 - choose_ASCII.width/2);
        curH = curH + choose_ASCII.height + 3;
        curH = curH + 11;
        for (int i = 0; i < length(list_perso); i++) {
            applyPatch(choice, list_perso[i], curH, 20 + ((witdh_last) + 20*i));
            applyPatch(prompt, getNumber(i+1, ANSI_WHITE), 0, 20 + ((witdh_last) + 20*i));
            witdh_last = witdh_last + list_perso[i].width;
        }
        applyPatch(main, choice, 0, 0);
        applyPatch(main, prompt, choice.height, 0, false);
        
        do {
            refresh(main);

            print("Choose your character : ");

            r = readInt();

        } while(r < 1 || r > 4);

        witdh_last = 0;
        for (int i = 0; i < length(list_perso); i++) {
            if (i != r-1) {
                removePatch(main, list_perso[i], curH, 20 + ((witdh_last) + 20*i), true);
            }
            else {
                hchoice = curH;
                wchoice = 20 + ((witdh_last) + 20*i);
            }
            witdh_last = witdh_last + list_perso[i].width;
        }
        choice = newScreen((main.height/4) * 3, main.width);
        removePatch(main, prompt, choice.height, 0);
        removePatch(main, choice, 0, 0);
        midleh = main.height/2 - list_perso[r-1].height/2;
        midlew = main.width/2 - list_perso[r-1].width/2;
        int pos = hchoice - midleh;
        while (pos != 0) {
            pos = hchoice - midleh;
            if (pos < 0) {
                if (pos < -2) {
                    moveBottom(main, list_perso[r-1], hchoice, wchoice );
                    moveBottom(main, list_perso[r-1], hchoice+1, wchoice);
                    hchoice =  hchoice + 2;
                }
                moveBottom(main, list_perso[r-1], hchoice, wchoice );
                hchoice =  hchoice + 1;
            }
            else if (pos > 0) {
                if (pos > 2) {
                    moveTop(main, list_perso[r-1], hchoice, wchoice);
                    moveTop(main, list_perso[r-1], hchoice-1, wchoice);
                    hchoice = hchoice - 2;
                }
                moveTop(main, list_perso[r-1], hchoice, wchoice);
                hchoice = hchoice - 1;
            }
            print(toString(main));
        }
        pos = wchoice - midlew;
        while (pos != 0) {
            pos = wchoice - midlew;
            if (pos < 0) {
                if (pos < -2) {
                    moveRight(main, list_perso[r-1], hchoice, wchoice );
                    moveRight(main, list_perso[r-1], hchoice, wchoice+1);
                    wchoice =  wchoice + 2;
                }
                moveRight(main, list_perso[r-1], hchoice, wchoice );
                wchoice =  wchoice + 1;
            }
            else if (pos > 0) {
                if (pos > 2) {
                    moveLeft(main, list_perso[r-1], hchoice, wchoice);
                    moveLeft(main, list_perso[r-1], hchoice, wchoice-1);
                    wchoice = wchoice - 2;
                }
                moveLeft(main, list_perso[r-1], hchoice, wchoice);
                wchoice = wchoice - 1;
            }
            print(toString(main));
        }
        refresh(main);
        return list_perso[r-1];

    }

    String randomChoice(String[] list) {
        int choice =(int) ( random() * length(list));
        return list[choice];
    }

    Mob randomChoice(Mob[] list) {
        int choice =(int) ( random() * length(list));  
        return list[choice];
    }

    Mob[] genMob(Screen main , int nombre) {
        loadMob();
        Mob[] lsMob = new Mob[nombre];
        Mob mob ;
        int posy_last = 0;
        int cpt = 0;
        while (cpt < nombre && posy_last < main.height) {
            mob = copyMob(randomChoice(listMob));
            mob.posx = main.width - mob.visuel.width - 30;
            print(posy_last + main.height/nombre - mob.visuel.height/2);
            mob.posy = posy_last + main.height/(nombre * 2) - mob.visuel.height/2;
            applyPatch(main, mob.visuel, mob.posy, main.height+mob.posx);
            moveTo(main, mob.visuel, mob.posy, main.height+mob.posx, mob.posy, mob.posx, 5);
            lsMob[cpt] = mob;
            cpt = cpt + 1;
            posy_last = posy_last + main.height/nombre;
            refresh(main);
        }
        return lsMob;
    }

    boolean allDead(Mob[] list) {
        boolean result = true;
        int cpt = 0;
        while (cpt < length(list) && result) {
            result = list[cpt].dead;
            cpt = cpt + 1;
        }
        return result;
    }

    int chooseNumber(int min, int max) {
        int result = 0;
        do {
            result = readInt();
        } while (result < min || result > max);
        return result;
    }

    void updateMobHpBar(Screen main, Mob mob) {       
        Screen hpBar = newScreen(1, mob.hp*20/mob.initialHp);
        removePatch(main, newScreen(1, 20), mob.posy + mob.visuel.height, mob.posx);
        if (mob.hp > (mob.initialHp/4)*3) {
            drawHorizontalLine(hpBar, 0, ANSI_GREEN);
        }
        else if (mob.hp > (mob.initialHp/4)) {
            drawHorizontalLine(hpBar, 0, ANSI_YELLOW);
        }
        else {
            drawHorizontalLine(hpBar, 0, ANSI_RED);
        }
        applyPatch(main, hpBar, mob.posy + mob.visuel.height, mob.posx);
    }

    void updateMobBattle(Screen main, Mob[] lsMob) {
        Mob mob;
        for (int i = 0; i < length(lsMob); i++) {
            mob = lsMob[i];
            if (mob.hp <= 0 && !mob.dead) {
                removePatch(main, mob.visuel, mob.posy, mob.posx, true);
                removePatch(main, newScreen(1, 20), mob.posy + mob.visuel.height, mob.posx);

                mob.dead = true;
                mob.hp = 0;
                
            }
            else {
                updateMobHpBar(main, mob);
            }
            
        }
    }

    boolean genWawe(Screen main, int waweNumber, int difficulty) {
        Mob[] listToDefeat = genMob(main, waweNumber);
        boolean gameOver = false;
        int cpt = 0;
        while (!allDead(listToDefeat) && !gameOver ) {
            removePatch(main, newScreen(7, 8), 2, 10);
            applyPatch(main, getNumber(waweNumber, ANSI_TEXT_DEFAULT_COLOR), 2, 10);
            updateMobBattle(main, listToDefeat);
            refresh(main);
            println("Qui voulez vous attaquer ? ");
            int choice = chooseNumber(1, length(listToDefeat)) - 1;
            if (!listToDefeat[choice].dead) {
                refresh(main);
                println("Vous attaquez le mob " + (choice + 1));
                listToDefeat[choice].hp = listToDefeat[choice].hp - 2;
                updateMobBattle(main, listToDefeat);
                refresh(main);
                cpt = cpt + 1;
            }
            
        }
        return gameOver;


    }

    int sumWidth(Screen[] screens) {
        int result = 0;
        for (int i = 0; i < length(screens); i++) {
            if (screens[i] != null ) {
                result = result + screens[i].width;
            }
        }
        return result;
    }

    Screen genText(String text, String color) {
        if (equals(color, "")) {
            color = ANSI_TEXT_DEFAULT_COLOR;
        }
        int maxheight = 0;
        char current;
        int lastWith = 0;
        Screen[] listASCII =  new Screen[length(text)];
        for (int i = 0; i < length(listASCII); i++) {
            current = charAt(text, i);
            if ((current >= 'a' && current <= 'z') || (current >= 'A' && current <= 'Z') || current == ' '){
                if (current == ' ') {
                    listASCII[i] = loadASCII(getletterPath(current), color);
                }
                else {
                    current = charAt(toUpperCase(current + ""), 0);
                    listASCII[i] = loadASCII(getletterPath(current), color);
                }
                
                if (listASCII[i].height > maxheight) {
                    maxheight = listASCII[i].height;
                }
            }
        }
        Screen generatedtext = newScreen(maxheight, sumWidth(listASCII));
        for (int i = 0; i < length(listASCII); i++) {
            if (listASCII[i] != null && listASCII[i].screen != null) {
                applyPatch(generatedtext, listASCII[i], 0, lastWith);
                lastWith = lastWith + listASCII[i].width;
            }
        }
         return generatedtext;

    }

    boolean genLevel(Screen main, int level) {
        int cpt = 0;
        boolean gameOver = false;
        while (cpt < 3 && !gameOver) {
            gameOver = genWawe(main, cpt+1, 1);
            cpt = cpt + 1;
        }
        return gameOver;
    }

    // boolean challenge(int min, int max) {
    //     int nb1;
    //     int nb2;
    //     if (min < 0) {
    //         nb1 = random(-min, max+(-min)) + min;
    //         nb2 = random(-min, max+(-min)) + min;
    //     }
    //     else {
    //         nb1 = random(min, max);
    //         nb2 = random(min, max);
    //     }

    //     int response ;
    //     print("Reponse : ");
    //     response = readInt();
    //     return response == nb1 + nb2;
    // }   

    void algorithm() {
        loadMob();
        Screen main = newScreen(51,250);
        boolean gameOver = false;
        Player player;
        int level = 1;

        afficherLogo(main);
        chooseCharacter(main);
        while (!gameOver) {
            genLevel(main, level);
        }
        //genWawe(main, 2, 1);
        refresh(main);
    }


    void _algorithm() {
        refresh(genText("prout", ""));
    }

}