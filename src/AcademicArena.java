class AcademicArena extends Program {



    char EMPTY = ' ';
    Pixel EMPTYPIXEL = newPixelEmpty();
    String RESSOURCESDIR = "ressources";
    String LOGOPARTA = RESSOURCESDIR + "/" + "academic.txt";
    String LOGOPARTB = RESSOURCESDIR + "/" + "arena.txt";
    String PLAYER = RESSOURCESDIR + "/" + "player.txt";

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
        p.c = ANSI_TEXT_DEFAULT_COLOR + EMPTY;
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
            result = result + '\n';
        }
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

    Screen loadASCII(String text, String color) {
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
        applyPatch(main, newScreen(patch.height, patch.width), h, w);
    }

    // void moveRight(Screen main, Screen patch, int curH, int curW, int w) {
    //     for (int i = curW; i < w+1; i++) {
            
    //         applyPatch(main, patch, curH, i);
    //         print(toString(main));
    //         removePatch(main, patch, curH, i);
            
    //     }
    //     applyPatch(main, patch, curH, w);
    //     print(toString(main));
    // }

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

    // void moveLeft(Screen main, Screen patch, int curH, int curW, int w) {
    //     for (int i = curW; i > w-1; i--) {
            
    //         applyPatch(main, patch, curH, i);
    //         print(toString(main));
    //         removePatch(main, patch, curH, i);
            
    //     }
    //     applyPatch(main, patch, curH, w);
    //     print(toString(main));

    // }

    // void drawVerticalLine(Screen main, int w) {
    //     Screen line = newScreen(main.height, 1);
    //     for (int i = 0; i < line.height; i++) {

    //     }
    // }

    void afficherLogo(Screen main){
        Screen partA = loadASCII(fileAsString(LOGOPARTA), ANSI_RED);
        Screen partB = loadASCII(fileAsString(LOGOPARTB), ANSI_YELLOW);
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

        // for (int i = 0; i < 50; i++) {
        //     moveTop(main, partA, posy_A - i, posX_A) ;
        //     moveBottom(main, partB, posy_B + i, posX_B);
        //     println(toString(main));
        // }


    }

    void mainMenue(Screen main) {
        Screen choice = newScreen(25, 120);
        Screen player = newScreen(25, 114);

        Screen player_ASCII = loadASCII(fileAsString(PLAYER), ANSI_BLUE);
        applyPatch(player, player_ASCII, 2, 20);

        applyPatch(main, player, 25, 120, false);

        println(toString(main));
        

    }




    void algorithm() {
        Screen sr = newScreen(50,204);
        // String f = fileAsString("ressources/academic.txt");
        // Screen s = loadASCII(f);
        // text("red");
        // // print(toString(s));
        // f = fileAsString("ressources/arena.txt");
        // s = loadASCII(f);
        // text("yellow");
        // // print(toString(s));

        afficherLogo(sr);
        mainMenue(sr);
        // println((isEmpty('⠀')));

    }
    // boolean finished = false;

    // void algorithm() {
    //     // On active l’écoute des évènements clavier
    //     enableKeyTypedInConsole(true);
    //     while (!finished) {
    //         delay(500);
    //         }
    //     }
    //     // Fonction définissant ce qui doit être fait
    //     // lorsqu’une touche est pressée par l’utilisateur
    //     void keyTypedInConsole(char key) {
    //         println("You pressed key with code: " + key
    //         + " (press 'q' to quit)");
    //         switch (key) {
    //             // ANSI_UP est une constante correspondant
    //             // au code ASCII de la flèche HAUT
    //             case ANSI_UP:
    //                 println("That's the UP arrow !");
    //                 break;
    //             // …
    //             case 'q' :
    //                 println("Ok, quitting ...");
    //                 finished = true;
    //             }
    //     }

}