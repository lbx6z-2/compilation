//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short PLUSONE=262;
public final static short MINUSONE=263;
public final static short NULL=264;
public final static short EXTENDS=265;
public final static short THIS=266;
public final static short WHILE=267;
public final static short FOR=268;
public final static short REPEAT=269;
public final static short UNTIL=270;
public final static short IF=271;
public final static short ELSE=272;
public final static short RETURN=273;
public final static short BREAK=274;
public final static short NEW=275;
public final static short SWITCH=276;
public final static short CASE=277;
public final static short DEFAULT=278;
public final static short PRINT=279;
public final static short READ_INTEGER=280;
public final static short READ_LINE=281;
public final static short LITERAL=282;
public final static short IDENTIFIER=283;
public final static short AND=284;
public final static short OR=285;
public final static short STATIC=286;
public final static short INSTANCEOF=287;
public final static short LESS_EQUAL=288;
public final static short GREATER_EQUAL=289;
public final static short EQUAL=290;
public final static short NOT_EQUAL=291;
public final static short DEC=292;
public final static short INC=293;
public final static short UMINUS=294;
public final static short EMPTY=295;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   26,   26,   23,   23,   27,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   25,   25,   25,   25,   25,   29,   29,   28,
   28,   30,   30,   16,   17,   18,   22,   15,   31,   31,
   20,   20,   21,   19,   32,   33,   33,   33,   34,   35,
   36,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    2,    1,    1,    2,    2,    2,    1,    3,    1,    0,
    2,    0,    2,    4,    5,    1,    1,    1,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    2,    3,    3,    1,    4,    5,    6,
    5,    5,    1,    2,    2,    2,    2,    1,    1,    1,
    0,    3,    1,    5,    6,    9,    1,    6,    2,    0,
    2,    1,    4,    5,    3,    2,    2,    0,    4,    3,
    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   79,   67,    0,    0,    0,
    0,    0,   87,    0,    0,    0,    0,    0,   78,    0,
    0,    0,    0,    0,    0,   24,   27,   37,   25,    0,
   29,   30,    0,   32,   33,    0,    0,    0,    0,    0,
    0,    0,   73,   48,    0,    0,    0,    0,   46,    0,
   47,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   28,   31,   34,   35,   36,    0,
    0,    0,    0,    0,    0,    0,   75,   74,    0,    0,
    0,    0,    0,    0,    0,   41,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   65,   66,
    0,    0,   62,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   68,    0,    0,    0,   93,    0,
    0,   44,    0,    0,    0,   84,    0,    0,    0,   69,
   98,   94,    0,    0,   71,    0,   45,    0,   85,    0,
   88,    0,   70,    0,   89,    0,    0,   95,   96,   97,
    0,    0,   26,   86,   26,    0,  100,   99,
};
final static short yydgoto[] = {                          2,
    3,    4,   67,   20,   33,    8,   11,   22,   34,   35,
   68,  206,   69,   70,   71,   72,   73,   74,   75,   76,
   77,   78,   89,   80,   91,   82,   83,  174,   84,  138,
  191,  182,  192,  199,  200,  207,
};
final static short yysindex[] = {                      -232,
 -249,    0, -232,    0, -230,    0, -244,  -82,    0,    0,
  307,    0,    0,    0,    0, -235, -202,    0,    0,    3,
  -90,    0,    0,  -89,    0,   12,  -22,   30, -202,    0,
 -202,    0,  -69,   32,   31,   35,    0,  -44, -202,  -44,
    0,    0,    0,    0,    4,    0,    0,   43,   44,   41,
   45,   85,    0, -137,   48,   49,   51,   53,    0,   54,
   85,   85,   85,   85,   63,    0,    0,    0,    0,   36,
    0,    0,   38,    0,    0,   39,   42,   46,   52,  605,
    0, -183,    0,    0,   85,   85, -166,   85,    0,  605,
    0,   67,   18,   85,   85,   69,   73,   85,  -37,  -37,
  -40,  -40, -168,  160,    0,    0,    0,    0,    0,   85,
   85,   85,   85,   85,   85,   85,    0,    0,   85,   85,
   85,   85,   85,   85,   85,    0,   85,   85,   76,  394,
   60,   99,  418,   90,   66,  429,  605,  -17,    0,    0,
  451,  100,    0,  605,  812,  750,  -26,  -26,  839,  839,
   -4,   -4,  -40,  -40,  -40,  -26,  -26,  481,  492,   85,
   41,   85,   85,   41,    0,  516,   17,   85,    0, -157,
   85,    0,   85,  101,  103,    0,  543,  554, -129,    0,
    0,    0,  605,  107,    0,  605,    0,   85,    0,   41,
    0, -122,    0,  116,    0,   85,  108,    0,    0,    0,
   41,  578,    0,    0,    0,   41,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  144,    0,   47,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  106,    0,    0,  133,    0,
  133,    0,    0,    0,  142,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -54,    0,    0,    0,    0,  -54,
    0,  -51,    0,    0,    0,    0,    0,    0,    0,    0,
  -96,  -96,  -96,  -96,  -96,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  687,    0,
  126,    0,    0,    0,  -96,  -54,    0,  -96,    0,  131,
    0,    0,    0,  -96,  -96,    0,    0,  -96,  863,  890,
  899,  944,    0,    0,    0,    0,    0,    0,    0,  -96,
  -96,  -96,  -96,  -96,  -96,  -96,    0,    0,  -96,  -96,
  -96,  -96,  -96,  -96,  -96,    0,  -96,  -96,   91,    0,
    0,    0,    0,    0,  -96,    0,   20,    0,    0,    0,
    0,    0,    0,  -23,    9,  443,  137,  986,   19,  327,
  117, 1049,  953, 1013, 1022, 1075, 1077,    0,    0,  -28,
  -54,  -96,  -96,  -54,    0,    0,    0,  -96,    0,    0,
  -96,    0,  -96,    0,  150,    0,    0,    0,  -33,    0,
    0,    0,   25,    0,    0,  -13,    0,  -27,    0,  -54,
    0,    0,    0,    0,    0,  -96,    0,    0,    0,    0,
  -54,    0,    0,    0,    0,   86,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  189,  187,    1,   -7,    0,    0,    0,  168,    0,
  -15,  163,  499,  -71,    0,    0,    0,    0,    0,    0,
    0,    0,  465, 1284,  471,    0,    0,    0,    0,   55,
    0,    0,    0,    0,    0,    7,
};
final static int YYTABLESIZE=1480;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         90,
   27,   27,  198,   21,   40,  126,   90,   92,  126,   24,
  123,   90,   81,   40,  131,  121,  119,   38,  120,  126,
  122,   27,   42,  169,   44,   90,  168,   72,    1,   32,
   72,   32,  123,    5,    7,   38,   64,  121,    9,   43,
   10,  126,  122,   65,   72,   72,   93,   23,   63,   60,
  127,   29,   60,  127,   12,   13,   14,   15,   16,   54,
   83,   25,   54,   83,  127,   82,   60,   60,   82,   31,
   30,   60,   38,   64,   39,   40,   54,   54,   41,   72,
   65,   54,   85,   86,   88,   63,  127,   94,   95,   90,
   96,   90,   97,   98,  105,   64,  106,  107,   64,  129,
  108,   60,   65,  132,  109,   65,  134,   63,  135,  139,
   63,   54,  110,  140,  142,  160,  194,   64,  162,   12,
   13,   14,   15,   16,   65,  184,   41,   43,   66,   63,
  165,   43,   43,   43,   43,   43,   43,   43,  163,  181,
  171,  187,  190,    1,   40,   92,  168,  193,   43,   43,
   43,   43,   43,   43,  196,  197,  201,   49,   30,   49,
   49,   49,   47,   41,    5,  203,   39,   47,   47,   14,
   47,   47,   47,   19,   49,   49,   49,   58,   49,   49,
   58,   43,   18,   43,   39,   47,   42,   47,   47,   91,
   80,    6,   26,   28,   58,   58,  123,   19,   36,   58,
  143,  121,  119,   45,  120,  126,  122,    0,    0,   49,
  101,  208,    0,   37,  175,    0,   47,    0,    0,  125,
    0,  124,  128,   90,   90,   90,   90,   90,   42,   58,
   90,   42,   90,   90,   90,   90,   90,   90,    0,   90,
   90,   90,   90,   90,   90,   90,   90,   90,   90,   90,
  127,  117,  118,   90,   42,   42,    0,    0,   90,   90,
   12,   13,   14,   15,   16,  117,  118,   46,    0,   47,
   48,   49,   50,    0,   51,    0,   52,   53,   54,   55,
    0,    0,   56,   57,   58,   59,    0,  117,  118,    0,
   60,    0,   60,   60,    0,   61,   62,   12,   13,   14,
   15,   16,   54,   54,   46,    0,   47,   48,   49,   50,
    0,   51,    0,   52,   53,   54,   55,    0,    0,   56,
   57,   58,   59,  103,    0,    0,   46,   60,   47,   46,
    0,   47,   61,   62,    0,    0,    0,   54,    0,    0,
   54,    0,   57,   58,   59,   57,   58,   59,   46,   60,
   47,    0,   60,    0,   61,   62,    0,   61,   62,   54,
    0,    0,  101,  101,   57,   58,   59,   55,   42,    0,
   55,   60,    0,    0,   43,   43,   61,   62,   43,   43,
   43,   43,   43,   43,   55,   55,    0,    0,    0,   55,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,   49,    0,    0,   49,   49,   49,   49,    0,   47,
   47,    0,    0,   47,   47,   47,   47,   47,   47,   55,
   58,   58,    0,    0,    0,    0,   58,   58,    0,    0,
  123,   18,    0,    0,  161,  121,  119,    0,  120,  126,
  122,    0,    0,  111,  112,    0,    0,  113,  114,  115,
  116,  117,  118,  125,  123,  124,  128,    0,  164,  121,
  119,    0,  120,  126,  122,  123,    0,    0,    0,  167,
  121,  119,    0,  120,  126,  122,    0,  125,    0,  124,
  128,    0,    0,   61,  127,    0,   61,  123,  125,    0,
  124,  128,  121,  119,  170,  120,  126,  122,    0,    0,
   61,   61,    0,    0,    0,   61,    0,    0,  127,   79,
  125,    0,  124,  128,   79,   81,    0,  123,    0,  127,
   81,    0,  121,  119,    0,  120,  126,  122,  123,    0,
    0,    0,    0,  121,  119,   61,  120,  126,  122,    0,
  125,  127,  124,  128,    0,    0,    0,    0,   87,  173,
   79,  125,  123,  124,  128,    0,   81,  121,  119,    0,
  120,  126,  122,   12,   13,   14,   15,   16,    0,    0,
    0,  127,    0,  172,    0,  125,    0,  124,  128,  123,
    0,    0,  127,    0,  121,  119,    0,  120,  126,  122,
  123,    0,   17,    0,  189,  121,  119,    0,  120,  126,
  122,  188,  125,    0,  124,  128,  127,    0,  180,    0,
   55,   55,    0,  125,  123,  124,  128,    0,    0,  121,
  119,    0,  120,  126,  122,   79,    0,    0,   79,    0,
    0,   81,    0,  127,   81,  205,    0,  125,    0,  124,
  128,  123,    0,    0,  127,    0,  121,  119,    0,  120,
  126,  122,   79,    0,   79,    0,    0,    0,   81,  176,
   81,    0,  179,    0,  125,   79,  124,  128,  127,    0,
   79,   81,    0,    0,    0,    0,   81,  111,  112,    0,
    0,  113,  114,  115,  116,  117,  118,    0,  195,    0,
    0,    0,    0,    0,    0,  127,    0,    0,    0,  204,
    0,  111,  112,    0,    0,  113,  114,  115,  116,  117,
  118,    0,  111,  112,    0,    0,  113,  114,  115,  116,
  117,  118,    0,   46,    0,    0,    0,   61,   46,   46,
    0,   46,   46,   46,  111,  112,    0,    0,  113,  114,
  115,  116,  117,  118,    0,    0,   46,    0,   46,   46,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  111,  112,    0,    0,  113,  114,
  115,  116,  117,  118,    0,  111,  112,   46,    0,  113,
  114,  115,  116,  117,  118,    0,  123,    0,    0,    0,
    0,  121,  119,    0,  120,  126,  122,    0,    0,  111,
  112,    0,    0,  113,  114,  115,  116,  117,  118,  125,
    0,  124,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  111,  112,    0,    0,
  113,  114,  115,  116,  117,  118,    0,  111,  112,    0,
  127,  113,  114,  115,  116,  117,  118,    0,  123,    0,
    0,    0,    0,  121,  119,    0,  120,  126,  122,    0,
    0,  111,  112,    0,    0,  113,  114,  115,  116,  117,
  118,  125,    0,  124,    0,  123,    0,    0,    0,    0,
  121,  119,    0,  120,  126,  122,    0,    0,  111,  112,
    0,    0,  113,  114,  115,  116,  117,  118,  125,   77,
  124,    0,  127,   77,   77,   77,   77,   77,    0,   77,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   77,   77,   77,    0,   77,   77,   76,    0,    0,  127,
   76,   76,   76,   76,   76,   63,   76,    0,    0,   63,
   63,   63,   63,   63,    0,   63,    0,   76,   76,   76,
    0,   76,   76,    0,    0,   77,   63,   63,   63,    0,
   63,   63,    0,    0,    0,    0,    0,    0,    0,    0,
   46,   46,    0,    0,   46,   46,   46,   46,   46,   46,
   64,    0,   76,    0,   64,   64,   64,   64,   64,   51,
   64,   63,    0,   51,   51,   51,   51,   51,    0,   51,
    0,   64,   64,   64,    0,   64,   64,    0,    0,    0,
   51,   51,   51,    0,   51,   51,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   59,    0,    0,   59,
    0,    0,    0,  111,    0,    0,   64,  113,  114,  115,
  116,  117,  118,   59,   59,   51,    0,    0,   59,   52,
    0,    0,    0,   52,   52,   52,   52,   52,   53,   52,
    0,    0,   53,   53,   53,   53,   53,    0,   53,    0,
   52,   52,   52,    0,   52,   52,    0,    0,   59,   53,
   53,   53,    0,   53,   53,    0,    0,    0,    0,   50,
    0,   50,   50,   50,    0,    0,    0,    0,    0,  113,
  114,  115,  116,  117,  118,   52,   50,   50,   50,    0,
   50,   50,    0,    0,   53,   57,    0,   56,   57,    0,
   56,    0,    0,    0,    0,    0,  113,  114,    0,    0,
  117,  118,   57,   57,   56,   56,    0,   57,    0,   56,
    0,   50,    0,    0,    0,    0,   77,   77,    0,    0,
   77,   77,   77,   77,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   57,    0,   56,
    0,    0,    0,   76,   76,    0,    0,   76,   76,   76,
   76,    0,   63,   63,    0,    0,   63,   63,   63,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   64,   64,    0,
    0,   64,   64,   64,   64,    0,   51,   51,    0,    0,
   51,   51,   51,   51,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   59,
   59,    0,    0,    0,    0,   59,   59,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   52,   52,    0,    0,
   52,   52,   52,   52,    0,   53,   53,    0,    0,   53,
   53,   53,   53,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   50,   50,    0,   90,   50,   50,   50,   50,
    0,    0,    0,    0,   99,  100,  101,  102,  104,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   57,   57,
   56,   56,    0,    0,   57,   57,   56,   56,  130,    0,
    0,  133,    0,    0,    0,    0,    0,  136,  137,    0,
    0,  141,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  144,  145,  146,  147,  148,  149,  150,
    0,    0,  151,  152,  153,  154,  155,  156,  157,    0,
  158,  159,    0,    0,    0,    0,    0,    0,  166,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  137,    0,  177,  178,    0,    0,    0,
    0,  183,    0,    0,  185,    0,  186,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  202,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   91,   91,  125,   11,   59,   46,   40,   59,   46,   17,
   37,   45,   41,   41,   86,   42,   43,   41,   45,   46,
   47,   91,   38,   41,   40,   59,   44,   41,  261,   29,
   44,   31,   37,  283,  265,   59,   33,   42,  283,   39,
  123,   46,   47,   40,   58,   59,   54,  283,   45,   41,
   91,   40,   44,   91,  257,  258,  259,  260,  261,   41,
   41,   59,   44,   44,   91,   41,   58,   59,   44,   40,
   93,   63,   41,   33,   44,   41,   58,   59,  123,   93,
   40,   63,   40,   40,   40,   45,   91,   40,   40,  123,
   40,  125,   40,   40,   59,   33,   59,   59,   33,  283,
   59,   93,   40,  270,   59,   40,   40,   45,   91,   41,
   45,   93,   61,   41,  283,   40,  188,   33,   59,  257,
  258,  259,  260,  261,   40,  283,  123,   37,  125,   45,
   41,   41,   42,   43,   44,   45,   46,   47,   40,  123,
   41,   41,  272,    0,   59,  283,   44,   41,   58,   59,
   60,   61,   62,   63,  277,  278,   41,   41,   93,   43,
   44,   45,   37,  123,   59,   58,   41,   42,   43,  123,
   45,   46,   47,   41,   58,   59,   60,   41,   62,   63,
   44,   91,   41,   93,   59,   60,  283,   62,   63,   59,
   41,    3,  283,  283,   58,   59,   37,   11,   31,   63,
   41,   42,   43,   41,   45,   46,   47,   -1,   -1,   93,
  125,  205,   -1,  283,  160,   -1,   91,   -1,   -1,   60,
   -1,   62,   63,  257,  258,  259,  260,  261,  283,   93,
  264,  283,  266,  267,  268,  269,  270,  271,   -1,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
   91,  292,  293,  287,  283,  283,   -1,   -1,  292,  293,
  257,  258,  259,  260,  261,  292,  293,  264,   -1,  266,
  267,  268,  269,   -1,  271,   -1,  273,  274,  275,  276,
   -1,   -1,  279,  280,  281,  282,   -1,  292,  293,   -1,
  287,   -1,  284,  285,   -1,  292,  293,  257,  258,  259,
  260,  261,  284,  285,  264,   -1,  266,  267,  268,  269,
   -1,  271,   -1,  273,  274,  275,  276,   -1,   -1,  279,
  280,  281,  282,  261,   -1,   -1,  264,  287,  266,  264,
   -1,  266,  292,  293,   -1,   -1,   -1,  275,   -1,   -1,
  275,   -1,  280,  281,  282,  280,  281,  282,  264,  287,
  266,   -1,  287,   -1,  292,  293,   -1,  292,  293,  275,
   -1,   -1,  277,  278,  280,  281,  282,   41,  283,   -1,
   44,  287,   -1,   -1,  284,  285,  292,  293,  288,  289,
  290,  291,  292,  293,   58,   59,   -1,   -1,   -1,   63,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  284,  285,   -1,   -1,  288,  289,  290,  291,   -1,  284,
  285,   -1,   -1,  288,  289,  290,  291,  292,  293,   93,
  284,  285,   -1,   -1,   -1,   -1,  290,  291,   -1,   -1,
   37,  125,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,  284,  285,   -1,   -1,  288,  289,  290,
  291,  292,  293,   60,   37,   62,   63,   -1,   41,   42,
   43,   -1,   45,   46,   47,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,   60,   -1,   62,
   63,   -1,   -1,   41,   91,   -1,   44,   37,   60,   -1,
   62,   63,   42,   43,   44,   45,   46,   47,   -1,   -1,
   58,   59,   -1,   -1,   -1,   63,   -1,   -1,   91,   45,
   60,   -1,   62,   63,   50,   45,   -1,   37,   -1,   91,
   50,   -1,   42,   43,   -1,   45,   46,   47,   37,   -1,
   -1,   -1,   -1,   42,   43,   93,   45,   46,   47,   -1,
   60,   91,   62,   63,   -1,   -1,   -1,   -1,   50,   58,
   86,   60,   37,   62,   63,   -1,   86,   42,   43,   -1,
   45,   46,   47,  257,  258,  259,  260,  261,   -1,   -1,
   -1,   91,   -1,   93,   -1,   60,   -1,   62,   63,   37,
   -1,   -1,   91,   -1,   42,   43,   -1,   45,   46,   47,
   37,   -1,  286,   -1,   41,   42,   43,   -1,   45,   46,
   47,   59,   60,   -1,   62,   63,   91,   -1,   93,   -1,
  284,  285,   -1,   60,   37,   62,   63,   -1,   -1,   42,
   43,   -1,   45,   46,   47,  161,   -1,   -1,  164,   -1,
   -1,  161,   -1,   91,  164,   58,   -1,   60,   -1,   62,
   63,   37,   -1,   -1,   91,   -1,   42,   43,   -1,   45,
   46,   47,  188,   -1,  190,   -1,   -1,   -1,  188,  161,
  190,   -1,  164,   -1,   60,  201,   62,   63,   91,   -1,
  206,  201,   -1,   -1,   -1,   -1,  206,  284,  285,   -1,
   -1,  288,  289,  290,  291,  292,  293,   -1,  190,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,  201,
   -1,  284,  285,   -1,   -1,  288,  289,  290,  291,  292,
  293,   -1,  284,  285,   -1,   -1,  288,  289,  290,  291,
  292,  293,   -1,   37,   -1,   -1,   -1,  285,   42,   43,
   -1,   45,   46,   47,  284,  285,   -1,   -1,  288,  289,
  290,  291,  292,  293,   -1,   -1,   60,   -1,   62,   63,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  284,  285,   -1,   -1,  288,  289,
  290,  291,  292,  293,   -1,  284,  285,   91,   -1,  288,
  289,  290,  291,  292,  293,   -1,   37,   -1,   -1,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,  284,
  285,   -1,   -1,  288,  289,  290,  291,  292,  293,   60,
   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  284,  285,   -1,   -1,
  288,  289,  290,  291,  292,  293,   -1,  284,  285,   -1,
   91,  288,  289,  290,  291,  292,  293,   -1,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,  284,  285,   -1,   -1,  288,  289,  290,  291,  292,
  293,   60,   -1,   62,   -1,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,  284,  285,
   -1,   -1,  288,  289,  290,  291,  292,  293,   60,   37,
   62,   -1,   91,   41,   42,   43,   44,   45,   -1,   47,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   58,   59,   60,   -1,   62,   63,   37,   -1,   -1,   91,
   41,   42,   43,   44,   45,   37,   47,   -1,   -1,   41,
   42,   43,   44,   45,   -1,   47,   -1,   58,   59,   60,
   -1,   62,   63,   -1,   -1,   93,   58,   59,   60,   -1,
   62,   63,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  284,  285,   -1,   -1,  288,  289,  290,  291,  292,  293,
   37,   -1,   93,   -1,   41,   42,   43,   44,   45,   37,
   47,   93,   -1,   41,   42,   43,   44,   45,   -1,   47,
   -1,   58,   59,   60,   -1,   62,   63,   -1,   -1,   -1,
   58,   59,   60,   -1,   62,   63,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,
   -1,   -1,   -1,  284,   -1,   -1,   93,  288,  289,  290,
  291,  292,  293,   58,   59,   93,   -1,   -1,   63,   37,
   -1,   -1,   -1,   41,   42,   43,   44,   45,   37,   47,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   58,   59,   60,   -1,   62,   63,   -1,   -1,   93,   58,
   59,   60,   -1,   62,   63,   -1,   -1,   -1,   -1,   41,
   -1,   43,   44,   45,   -1,   -1,   -1,   -1,   -1,  288,
  289,  290,  291,  292,  293,   93,   58,   59,   60,   -1,
   62,   63,   -1,   -1,   93,   41,   -1,   41,   44,   -1,
   44,   -1,   -1,   -1,   -1,   -1,  288,  289,   -1,   -1,
  292,  293,   58,   59,   58,   59,   -1,   63,   -1,   63,
   -1,   93,   -1,   -1,   -1,   -1,  284,  285,   -1,   -1,
  288,  289,  290,  291,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   93,
   -1,   -1,   -1,  284,  285,   -1,   -1,  288,  289,  290,
  291,   -1,  284,  285,   -1,   -1,  288,  289,  290,  291,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  284,  285,   -1,
   -1,  288,  289,  290,  291,   -1,  284,  285,   -1,   -1,
  288,  289,  290,  291,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  284,
  285,   -1,   -1,   -1,   -1,  290,  291,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  284,  285,   -1,   -1,
  288,  289,  290,  291,   -1,  284,  285,   -1,   -1,  288,
  289,  290,  291,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  284,  285,   -1,   52,  288,  289,  290,  291,
   -1,   -1,   -1,   -1,   61,   62,   63,   64,   65,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  284,  285,
  284,  285,   -1,   -1,  290,  291,  290,  291,   85,   -1,
   -1,   88,   -1,   -1,   -1,   -1,   -1,   94,   95,   -1,
   -1,   98,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  110,  111,  112,  113,  114,  115,  116,
   -1,   -1,  119,  120,  121,  122,  123,  124,  125,   -1,
  127,  128,   -1,   -1,   -1,   -1,   -1,   -1,  135,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  160,   -1,  162,  163,   -1,   -1,   -1,
   -1,  168,   -1,   -1,  171,   -1,  173,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  196,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=295;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","PLUSONE","MINUSONE","NULL","EXTENDS","THIS","WHILE","FOR","REPEAT",
"UNTIL","IF","ELSE","RETURN","BREAK","NEW","SWITCH","CASE","DEFAULT","PRINT",
"READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR","STATIC",
"INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","DEC","INC",
"UMINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : RepeatStmt ';'",
"Stmt : ForStmt",
"Stmt : SwitchStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : RunableExpr",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : RunableExpr",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Expr : Expr '?' Expr ':' Expr",
"RunableExpr : Call",
"RunableExpr : Expr INC",
"RunableExpr : Expr DEC",
"RunableExpr : INC Expr",
"RunableExpr : DEC Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"RepeatStmt : REPEAT Stmt UNTIL '(' Expr ')'",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"SwitchStmt : SWITCH '(' Expr ')' SwitchBlock",
"SwitchBlock : '{' Cases '}'",
"Cases : Cases Case",
"Cases : Cases DefaultCase",
"Cases :",
"Case : CASE Expr ':' CaseBlock",
"DefaultCase : DEFAULT ':' CaseBlock",
"CaseBlock : StmtList",
};

//#line 503 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 714 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 55 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 61 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 65 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 75 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 81 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 85 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 89 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 93 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 97 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 101 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 107 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 113 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 117 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 123 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 127 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 131 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 139 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 20:
//#line 146 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 150 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 157 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 161 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 167 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 173 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 177 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 184 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 189 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 206 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 210 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 214 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 221 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 227 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 44:
//#line 234 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 45:
//#line 240 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 46:
//#line 250 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 49:
//#line 256 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 50:
//#line 260 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 264 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 268 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 272 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 276 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 280 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 284 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 288 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 292 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 296 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 300 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 304 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 308 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 63:
//#line 312 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 316 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 320 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 66:
//#line 324 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 67:
//#line 328 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 68:
//#line 332 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 69:
//#line 336 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 70:
//#line 340 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 71:
//#line 344 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 72:
//#line 348 "Parser.y"
{
                		yyval.expr = new Tree.Ternary(Tree.CONOP, val_peek(4).expr, val_peek(2).expr, val_peek(0).expr, val_peek(4).loc);
                	}
break;
case 74:
//#line 355 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.POSTINC, val_peek(1).expr, val_peek(1).loc);
                	}
break;
case 75:
//#line 359 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.POSTDEC, val_peek(1).expr, val_peek(1).loc);
                	}
break;
case 76:
//#line 363 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.PREINC, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 77:
//#line 367 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.PREDEC, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 78:
//#line 373 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 79:
//#line 377 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 81:
//#line 384 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 82:
//#line 391 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 83:
//#line 395 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 84:
//#line 402 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 85:
//#line 408 "Parser.y"
{
						yyval.stmt = new Tree.RepeatLoop(val_peek(1).expr, val_peek(4).stmt, val_peek(5).loc);
					}
break;
case 86:
//#line 414 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 87:
//#line 420 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 88:
//#line 426 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 89:
//#line 432 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 90:
//#line 436 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 91:
//#line 442 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 92:
//#line 446 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 93:
//#line 452 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 94:
//#line 458 "Parser.y"
{
                        yyval.stmt = new Switch(val_peek(2).expr, (SwitchBlock)val_peek(0).stmt, val_peek(4).loc);
                    }
break;
case 95:
//#line 464 "Parser.y"
{
                        yyval.stmt = new SwitchBlock(val_peek(1).slist, val_peek(2).loc);
                    }
break;
case 96:
//#line 470 "Parser.y"
{
                		yyval.slist.add(val_peek(0).stmt);
                	}
break;
case 97:
//#line 474 "Parser.y"
{
                		yyval.slist.add(val_peek(0).stmt);
                	}
break;
case 98:
//#line 478 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 99:
//#line 485 "Parser.y"
{
                        yyval.stmt = new Case(val_peek(2).expr, (CaseBlock)val_peek(0).stmt, val_peek(3).loc);
                    }
break;
case 100:
//#line 491 "Parser.y"
{
                        yyval.stmt = new DefaultCase((CaseBlock)val_peek(0).stmt, val_peek(2).loc);
                    }
break;
case 101:
//#line 497 "Parser.y"
{
                        yyval.stmt = new CaseBlock(val_peek(0).slist, null);
                    }
break;
//#line 1386 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
