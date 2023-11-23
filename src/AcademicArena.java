class AcademicArena extends Program {

    char EMPTY = ' ';
    String RESSOURCESDIR = "ressources";
    String LOGOPARTA = RESSOURCESDIR + "/" + "academic.txt";
    String LOGOPARTB = RESSOURCESDIR + "/" + "arena.txt";


    Screen newScreen(int height, int width) {
        Screen sr = new Screen();
        sr.height = height;
        sr.width = width;
        sr.screen = new char[height][width];
        for (int i = 0; i < length(sr.screen, 1); i++) {
            for (int j = 0; j < length(sr.screen, 2); j++) {
                sr.screen[i][j] = EMPTY;
            }
        }
        return sr;
    }

    String toString(Screen screen) {
        String result = "";
        for (int i = 0; i < length(screen.screen, 1); i++) {

            for (int j = 0; j < length(screen.screen, 2); j++) {
                result = result + screen.screen[i][j];
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

    Screen loadASCII(String text) {
        int height = count(text, '\n');
        int width = length(substring(text, 0, IndexFirst(text, '\n')));
        char[][] screen = new char[height][width];
        int cptW = 0;
        char current = '\0';
        int cpt = 0;
        for (int i = 0; i < height; i++) {
            while ( current != '\n' && cpt < length(text) && cptW < width) {
                current = charAt(text, cpt);
                screen[i][cptW] = current;
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

    void afficherLogo(Screen main){
        Screen partA = loadASCII(fileAsString(LOGOPARTA));
        Screen partB = loadASCII(fileAsString(LOGOPARTB));

        for (int i = -150; i < 40; i++) {
            moveRight(main, partA, 10, i);
            moveLeft(main, partB, 25, main.width-(i+125));
            println(toString(main));
        } 

        print("             Press enter to start                ");
        readString();

        for (int i = 0; i < 50; i++) {
            moveTop(main, partA, 10 - i, 40) ;
            moveBottom(main, partB, 25 + i, main.width -(40 + 125));
            println(toString(main));
        }


    }




    void algorithm() {
        Screen sr = newScreen(50,236);
        String f = fileAsString("ressources/academic.txt");
        Screen s = loadASCII(f);
        text("red");
        // print(toString(s));
        f = fileAsString("ressources/arena.txt");
        s = loadASCII(f);
        text("yellow");
        // print(toString(s));
        afficherLogo(sr);
    }



}