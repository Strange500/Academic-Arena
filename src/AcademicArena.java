class AcademicArena extends Program {



    char EMPTY = ' ';
    Pixel EMPTYPIXEL = newPixelEmpty();
    String RESSOURCESDIR = "ressources";
    String LOGOPARTA = RESSOURCESDIR + "/" + "academic.txt";
    String LOGOPARTB = RESSOURCESDIR + "/" + "arena.txt";
    String PLAYER = RESSOURCESDIR + "/" + "player.txt";
    String choosecharacter = RESSOURCESDIR + "/" + "choose.txt";
    String NUMBERDIR = RESSOURCESDIR + "/" + "numbers";

    char[] list_EMPTY = new char[]{' ', ' ', ' ', ' ', ' ',' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '　', '⠀'};

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

    boolean equals(Pixel p1, Pixel p2) {
        return charAt(p1.c, length(p1.c) - 1) ==  charAt(p2.c, length(p2.c) - 1);
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
        Screen n = newScreen(patch.height, patch.width);
        for (int i = 0; i < n.height+1; i++) {
            removePatch(n, n, i-1, 0);
            drawHorizontalLine(n, i);
            applyPatch(patch, n, 0, 0);
            applyPatch(main, patch, h, w);
            println(toString(main));
        }
        applyPatch(main, n, h, w);
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

    

    void drawVerticalLine(Screen main, int w, String color) {
        Screen line = newScreen(main.height, 1);
        for (int i = 0; i < line.height; i++) {
            line.screen[i][1] = newPixel('▐', color);
        }
        applyPatch(main, line, 0, w);
    }

    void drawVerticalLine(Screen main, int w) {
        Screen line = newScreen(main.height, 1);
        for (int i = 0; i < line.height; i++) {
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
            println(toString(main));
        } 

        print("             Press enter to start                ");
        readString();

        for (int i = 0; i < 50; i++) {
            moveTop(main, partA, posy_A - i, posX_A) ;
            moveBottom(main, partB, posy_B + i, posX_B);
            println(toString(main));
        }


    }

    void mainMenue(Screen main) {
        Screen choice = newScreen(25, 120);
        Screen player = newScreen(25, 114);

        Screen player_ASCII = loadASCII(PLAYER, ANSI_BLUE);
        applyPatch(player, player_ASCII, 2, 20);

        applyPatch(main, player, 25, 120, false);

        drawVerticalLine(main, 120);
        
        println(toString(main));
        

    }

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
        list_perso[1] = loadASCII(PLAYER, ANSI_GREEN);
        list_perso[2] = loadASCII(PLAYER, ANSI_YELLOW);
        list_perso[3] = loadASCII(PLAYER, ANSI_BLUE);


        Screen choose_ASCII = loadASCII(choosecharacter, ANSI_WHITE);
        applyPatch(choice, choose_ASCII, curH, choice.width/2 - choose_ASCII.width/2);
        curH = curH + choose_ASCII.height + 3;
        drawHorizontalLine(choice, curH);
        curH = curH + 11;

        for (int i = 0; i < length(list_perso); i++) {
            applyPatch(choice, list_perso[i], curH, 20 + ((witdh_last) + 20*i));
            applyPatch(prompt, getNumber(i+1, ANSI_WHITE), 0, 20 + ((witdh_last) + 20*i));
            witdh_last = witdh_last + list_perso[i].width;
        }
        applyPatch(main, choice, 0, 0);
        applyPatch(main, prompt, choice.height, 0, false);
        
        do {
            println(toString(main));

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
                moveBottom(main, list_perso[r-1], hchoice, wchoice );
                hchoice =  hchoice + 1;
            }
            else if (pos > 0) {
                moveTop(main, list_perso[r-1], hchoice, wchoice);
                hchoice = hchoice - 1;
            }
            print(toString(main));
        }
        pos = wchoice - midlew;
        while (pos != 0) {
            pos = wchoice - midlew;
            if (pos < 0) {
                moveRight(main, list_perso[r-1], hchoice, wchoice );
                wchoice =  wchoice + 1;
            }
            else if (pos > 0) {
                moveLeft(main, list_perso[r-1], hchoice, wchoice);
                wchoice = wchoice - 1;
            }
            print(toString(main));
        }
        println(toString(main));
        return list_perso[r-1];

    }



    void algorithm() {
        Screen sr = newScreen(53,204);
        // applyPatch(sr, loadASCII(PLAYER, ANSI_BLUE), 0, 0);
        // println(toString(sr));



        afficherLogo(sr);
        mainMenue(sr);
        chooseCharacter(sr);
        println(toString(sr));

    }

}