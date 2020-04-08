/* version 1.0
    made by Vassia Bonadrini
    09/04/2020
*/

import java.util.*;
import java.io.*;
import java.math.*;
import java.security.SecureRandom;
//algo GCD, inverse modulaire avec euler (déjà codé),exponentiation modulaire,
public class crypto{
	public static void main(String[] args){
		String m="";
		Scanner sc;
		
		System.out.println();
		while(true){
			sc=new Scanner(System.in);
			System.out.println("Tapez 'initiation', 'chiffrement', 'dechiffrage', 'factoriser', 'premier' ou 'quitter'.");
			m=sc.nextLine();
			if(m.equals("initiation")){
				initiation();
			}
			else if(m.equals("chiffrement")){
				chiffrement();
			}
			else if(m.equals("dechiffrage")){
				dechiffrage();
			}
			else if(m.equals("factoriser")){
				factoriser();
			}
			else if(m.equals("quitter")){
				System.out.println("Aurevoir !");
				break;
			}
			else if(m.equals("premier")){
				premierAffiche();
			}
			else{
				System.out.println("je n'ai pas compris votre message.");
			}
		}
	}
	
	public static void initiation(){
		Scanner sc = new Scanner(System.in);
		int nomb=25,nbbits;
		BigInteger p=new BigInteger("1"),q=new BigInteger("1"),n=new BigInteger("1"),phi=new BigInteger("1"),e=new BigInteger("1"),d=new BigInteger("1"),find=new BigInteger("1");
		BigInteger mini;
		BigInteger[] mchiffre;
		AKS aks =new AKS();
		String premPhi="",m="",prem=premier(BigInteger.valueOf(2),nomb),premQ=prem;
		
		while(true){
			try{
				System.out.println("Sur combien de bits minimum voulez-vous que soit votre nombre n ? (les chars sont codés sur 16 bits)");
				nbbits=sc.nextInt();
				if (nbbits<=3){
					System.out.println("Votre nombre est trop petit\n");
				}
				else{
					break;
				}
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
		}
		
		mini=BigInteger.valueOf(2).pow(nbbits-1);
		
		while(true){
			try{
				System.out.println("Veuillez saisir un nombre premier svp");
				System.out.println("Par exemple :");
				System.out.println(prem);
				p=sc.nextBigInteger();
				if (!aks.checkIsPrime(p)){
					System.out.println("Vous n'avez pas saisi un nombre premier !\n");
				}
				else{
					break;
				}
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
			catch (ArithmeticException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
			catch (NumberFormatException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
		}
		
		if (p.compareTo(mini)==-1){
			find=mini.divide(p);
			premQ=premier(find,nomb);
		}
		while(true){
			try{
				System.out.println("Veuillez saisir un autre nombre premier distinct du premier svp tel que le produit soit supérieur ou égal à 2^(nombre de bits-1).");
				System.out.println("Par exemple :");
				System.out.println(premQ);
				q=sc.nextBigInteger();
				if (!aks.checkIsPrime(q)){
					System.out.println("Vous n'avez pas saisi un nombre premier !\n");
				}
				else if (q.equals(p)){
					System.out.println("Vous devez prendre des nombres premiers distincts !\n");
				}
				else if (q.compareTo(find)==-1){
					System.out.println("Vous devez prendre des nombres dont le produit est supérieur ou égal à 65 536 !\n");
				}
				else{
					break;
				}
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
			catch (ArithmeticException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
			catch (NumberFormatException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
		}
		
		System.out.println("Vous avez saisi : "+p+" et "+q);
		n=p.multiply(q);
		System.out.println("n = p*q = "+n);
		phi=(p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		System.out.println("Lorsque p et q sont 2 nombres premiers distincts alors l'indicatrice d'Euler phi(n) = (p-1)*(q-1) = "+phi);
		premPhi=premierAvec(phi,nomb);
		while(true){
			try{
				System.out.println("Veuillez saisir un entier naturel e qui est inférieur strictement a phi(n) et qui est premier avec phi(n)");
				System.out.println("Vous avez le choix entre par exemple:");
				System.out.println(premPhi);
				e=sc.nextBigInteger();
				if (!((e.gcd(phi)).equals(BigInteger.ONE))){
					//GCD perso
					System.out.println("Vous n'avez pas saisi un entier premier avec phi(n) !\n");
				}
				else if (!(e.compareTo(phi)==-1)){
					System.out.println("Vous n'avez pas saisi un entier inférieur a phi(n) !\n");
				}
				else{
					break;
				}
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
			catch (ArithmeticException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
			catch (NumberFormatException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
		}
	System.out.println("Vous avez choisi e = "+e);
	System.out.println("Nous devons maintenant calculer l'inverse modulaire de e dans Zphi(n) a l'aide de l'algorithme d'Euclide étendu.");
	d=e.modInverse(phi);
	// d=inverseE(e,phi);
	System.out.println("On trouve d = "+d);
	System.out.println("C'est fini !");
	System.out.println("vous pouvez dès a présent publier votre clé publique qui est (n,e) soit ici : ("+n+","+e+")");
	System.out.println("Et votre clé privée que vous devez absolument garder secrète est (n,d) (il faut garder secret d et phi(n)) : ("+n+","+d+")");
	System.out.println();
	
	System.out.println("Passons maintenant au chiffrement du message !");
	System.out.println();
	System.out.println("Entrez le message que vous voulez chiffrer :");
	sc = new Scanner(System.in);
	m=sc.nextLine();
	System.out.println();
	System.out.println("Un caractère en java est codé sur 16 bits. Pour éviter tout problèmes, les paquets mi doivent être inférieur strictement à n donc n doit être supérieur ou égale à 2^16 soit 65 536.");
	mchiffre=convertStringBigInteger(m);
	System.out.println("Votre message en code ASCII s'écrit comme suit :");
	System.out.println(convertString(mchiffre));
	System.out.println("Pour chiffrer notre message il faut maintenant faire m (le code en ASCII) à la puissance e modulo n.");
	System.out.println("e vaut "+e+" on ne peut pas calculer cette puissance dans un temps raisonnable !");
	System.out.println("Il faut donc passer par l'exponentiation modulaire.");
	System.out.println();
	System.out.println("Ton message chiffré est donc maintenant :");
	mchiffre=chiffrerMessage(mchiffre,e,n);
	System.out.println(convertString(mchiffre));
	
	System.out.println();
	System.out.println("Passons maintenant au déchiffrage du message qui est possible seulement par celui qui a la clé privée !");
	System.out.println("Le déchiffrage s'effectue par l'opération : c (code chiffré reçu) à la puissance d modulo n.");
	System.out.println();
	System.out.println("Le message que vous voulez déchiffrer est :");
	System.out.println(convertString(mchiffre));
	System.out.println();
	System.out.println("Le message déchiffré est :");
	mchiffre=chiffrerMessage(mchiffre,d,n);
	System.out.println(convertString(mchiffre));
	System.out.println("Repassons le en ASCII :");
	System.out.println(convertBigIntegerString(mchiffre));
	}
	
	public static void chiffrement(){
		BigInteger n,e;
		String m;
		Scanner sc = new Scanner(System.in);
		BigInteger[] mchiffre;
		AKS aks =new AKS();
		
		while(true){
			try{
				System.out.println("Veuillez saisir le n de la clé publique");
				n=sc.nextBigInteger();
				break;
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
			catch (ArithmeticException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
			catch (NumberFormatException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
		}
		
		while(true){
			try{
				System.out.println("Veuillez saisir le e de la clé publique");
				e=sc.nextBigInteger();
				break;				
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
			catch (ArithmeticException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
			catch (NumberFormatException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
		}
		
		System.out.println("Entrez le message que vous voulez chiffrer :");
		sc = new Scanner(System.in);
		m=sc.nextLine();
		System.out.println();
		mchiffre=convertStringBigInteger(m);
		System.out.println("Ton message chiffré est :");
		mchiffre=chiffrerMessage(mchiffre,e,n);
		System.out.println(convertString(mchiffre));
	}
	
	public static void dechiffrage(){
		BigInteger n,d;
		String m;
		Scanner sc = new Scanner(System.in);
		BigInteger[] mchiffre;
		AKS aks =new AKS();
		
		while(true){
			try{
				System.out.println("Veuillez saisir le n de la clé privée");
				n=sc.nextBigInteger();
				break;
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
			catch (ArithmeticException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
			catch (NumberFormatException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
		}
		
		while(true){
			try{
				System.out.println("Veuillez saisir le d de la clé privée");
				d=sc.nextBigInteger();
				break;				
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
			catch (ArithmeticException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
			catch (NumberFormatException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
		}
		
		System.out.println("Entrez le message que vous voulez déchiffrer :");
		sc = new Scanner(System.in);
		m=sc.nextLine();
		mchiffre=convertChiffrerBigInt(m);
		System.out.println();
		System.out.println("Ton message déchiffré est :");
		mchiffre=chiffrerMessage(mchiffre,d,n);
		System.out.println(convertBigIntegerString(mchiffre));
	}
	
	public static void premierAffiche(){
		Scanner sc=new Scanner(System.in);
		int nb;
		int deb;
		long start,duree;
		double s,min,h,mina,ha;
		
		while(true){
			try{
				System.out.println("Saisissez le nombre de nombre premier que vous voulez :");
				nb=sc.nextInt();
				if (nb<1){
					System.out.println("Vous n'avez pas saisi un nombre strictement positif !\n");
				}
				else{
					break;
				}
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
		}
		
		while(true){
			try{
				System.out.println("Saisissez le début de la recherche (quel nombre de départ, il peut ne pas être premier) :");
				deb=sc.nextInt();
				if (deb<0){
					System.out.println("Vous n'avez pas saisi un nombre positif !\n");
				}
				else{
					break;
				}
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
		}
		
		System.out.println();
		System.out.println("Voici les "+nb+" premier nombres premiers à partir de "+deb+" :");
		start = System.currentTimeMillis();
		premier(deb,nb);
		System.out.println();
		duree = System.currentTimeMillis() - start;
		s= (double)duree/(double)1000;
		min=s/60;
		mina=(double) Math.round(min * 1000) / 1000; 
		h=min/60;
		ha=(double) Math.round(h * 1000) / 1000; 
		System.out.println("Il a fallu "+duree+" millisecondes pour les trouver soit "+s+" secondes soit "+mina+" minutes soit "+ha+" heures.");
	}
	
	public static void factoriser(){
		Scanner sc=new Scanner(System.in);
		String m;
		BigInteger n;
		int b;
		long M1,M2,M3,M4,M5,M6,M7,M8,M9;
		BigInteger res1,res2,res3,res4,res5,res6,res7,res8,res9;
		ReturningValues RV1,RV2,RV3,RV4,RV5,RV6,RV7,RV8,RV9;
		boolean f;
		
		while(true){
			try{
				System.out.println("Veuillez saisir votre nombre à décomposer (svp vérifiez qu'il soit décomposable):");
				n=sc.nextBigInteger();
				if (n.compareTo(BigInteger.valueOf(6))==-1){
					System.out.println("Votre nombre est déjà décomposé !\n");
				}
				else{
					break;
				}
			}
			catch (InputMismatchException E){
				System.out.println("Ceci n'est pas un nombre !\n");
				sc = new Scanner(System.in);
			}
			catch (ArithmeticException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
			catch (NumberFormatException E){
				System.out.println("Ceci n'est pas un nombre valide !\n");
				sc = new Scanner(System.in);
			}
		}
		
		while(true){
			sc=new Scanner(System.in);
			
			System.out.println("Avec quelle méthode voulez vous factoriser votre entier ?");
			System.out.println("'toutes', 'p-1 reille', 'p-1 uvsq', 'p-1 sans borne', 'crible quadratique', 'rho', 'courbes elliptiques', 'p+1', 'p+1 sans borne' ou vous pouvez 'retour'.");
			m=sc.nextLine();
			if(m.equals("toutes")){
				while(true){
					try{
						System.out.println("Veuillez saisir la borne (non comprise ex si vous mettez 5 alors B=2*3) pour les méthodes qui en ont besoin :");
						b=sc.nextInt();
						if (b<3){
							System.out.println("Votre borne est trop petite !\n");
						}
						else{
							break;
						}
					}
					catch (InputMismatchException E){
						System.out.println("Ceci n'est pas un nombre !\n");
						sc = new Scanner(System.in);
					}
					catch (ArithmeticException E){
						System.out.println("Ceci n'est pas un nombre valide !\n");
						sc = new Scanner(System.in);
					}
					catch (NumberFormatException E){
						System.out.println("Ceci n'est pas un nombre valide !\n");
						sc = new Scanner(System.in);
					}
				}
				System.out.println();
				System.out.println("-----METHODE P-1 DE POLLARD VERSION REILLE-----");
				RV1=pollardPReille(n,b);
				M1=RV1.duree;
				res1=RV1.res;
				afficheResultat(n,res1);
				tempsCalcul(M1);
				System.out.println();
				System.out.println("-----METHODE P-1 DE POLLARD VERSION UNIVERSITE VERSAILLES SAINT QUENTIN-----");
				RV2=pollardPuvsq(n,b);
				M2=RV2.duree;
				res2=RV2.res;
				afficheResultat(n,res2);
				tempsCalcul(M2);
				System.out.println();
				System.out.println("-----METHODE P-1 DE POLLARD VERSION UNIVERSITE VERSAILLES SAINT QUENTIN SANS BORNE AVEC LE VRAI CALCUL DE m-----");
				RV3=pollardPuvsqSoluc(n,true);
				M3=RV3.duree;
				res3=RV3.res;
				afficheResultat(n,res3);
				tempsCalcul(M3);
				System.out.println();
				System.out.println("-----METHODE P-1 DE POLLARD VERSION UNIVERSITE VERSAILLES SAINT QUENTIN SANS BORNE AVEC LE e=B!-----");
				RV4=pollardPuvsqSoluc(n,false);
				M4=RV4.duree;
				res4=RV4.res;
				afficheResultat(n,res4);
				tempsCalcul(M4);
				System.out.println();
				System.out.println("-----METHODE P+1 DE WILLIAMS AVEC LE e=B!-----");
				RV5=williamsMinus1(n,b,false);
				M5=RV5.duree;
				res5=RV5.res;
				afficheResultat(n,res5);
				tempsCalcul(M5);
				System.out.println();
				System.out.println("-----METHODE P+1 DE WILLIAMS AVEC LE VRAI CALCUL DE m-----");
				RV6=williamsMinus1(n,b,true);
				M6=RV6.duree;
				res6=RV6.res;
				afficheResultat(n,res6);
				tempsCalcul(M6);
				System.out.println();
				System.out.println("-----METHODE P+1 DE WILLIAMS SANS BORNE AVEC e=B!-----");
				RV7=williamsMinus1Soluc(n,false);
				M7=RV7.duree;
				res7=RV7.res;
				afficheResultat(n,res7);
				tempsCalcul(M7);
				System.out.println();
				System.out.println("-----METHODE P+1 DE WILLIAMS SANS BORNE AVEC LE VRAI CALCUL DE m-----");
				RV8=williamsMinus1Soluc(n,true);
				M8=RV8.duree;
				res8=RV8.res;
				afficheResultat(n,res8);
				tempsCalcul(M8);
				System.out.println();
				System.out.println("-----METHODE RHO DE POLLARD-----");
				RV9=rho(n);
				M9=RV9.duree;
				res9=RV9.res;
				afficheResultat(n,res9);
				tempsCalcul(M9);
				System.out.println();
				System.out.println();
				System.out.println("-----BILAN-----");
				System.out.println();
				System.out.println("-----METHODE P-1 DE POLLARD VERSION REILLE-----");
				afficheResultat(n,res1);
				tempsCalcul(M1);
				System.out.println("-----METHODE P-1 DE POLLARD VERSION UNIVERSITE VERSAILLES SAINT QUENTIN-----");
				afficheResultat(n,res2);
				tempsCalcul(M2);
				System.out.println("-----METHODE P-1 DE POLLARD VERSION UNIVERSITE VERSAILLES SAINT QUENTIN SANS BORNE AVEC LE VRAI CALCUL DE m-----");
				afficheResultat(n,res3);
				tempsCalcul(M3);
				System.out.println("-----METHODE P-1 DE POLLARD VERSION UNIVERSITE VERSAILLES SAINT QUENTIN SANS BORNE AVEC LE e=B!-----");
				afficheResultat(n,res4);
				tempsCalcul(M4);
				System.out.println("-----METHODE P+1 DE WILLIAMS AVEC LE e=B!-----");
				afficheResultat(n,res5);
				tempsCalcul(M5);
				System.out.println("-----METHODE P+1 DE WILLIAMS AVEC LE VRAI CALCUL DE m-----");
				afficheResultat(n,res6);
				tempsCalcul(M6);
				System.out.println("-----METHODE P+1 DE WILLIAMS SANS BORNE AVEC e=B!-----");
				afficheResultat(n,res7);
				tempsCalcul(M7);
				System.out.println("-----METHODE P+1 DE WILLIAMS SANS BORNE AVEC LE VRAI CALCUL DE m-----");
				afficheResultat(n,res8);
				tempsCalcul(M8);
				System.out.println("-----METHODE RHO DE POLLARD-----");
				afficheResultat(n,res9);
				tempsCalcul(M9);
				System.out.println();
			}
			else if(m.equals("p-1 reille")){
				System.out.println("-----METHODE P-1 DE POLLARD VERSION REILLE-----");
				while(true){
					try{
						System.out.println("Veuillez saisir la borne (non comprise ex si vous mettez 5 alors B=2*3):");
						b=sc.nextInt();
						if (b<3){
							System.out.println("Votre borne est trop petite !\n");
						}
						else{
							break;
						}
					}
					catch (InputMismatchException E){
						System.out.println("Ceci n'est pas un nombre !\n");
						sc = new Scanner(System.in);
					}
					catch (ArithmeticException E){
						System.out.println("Ceci n'est pas un nombre valide !\n");
						sc = new Scanner(System.in);
					}
					catch (NumberFormatException E){
						System.out.println("Ceci n'est pas un nombre valide !\n");
						sc = new Scanner(System.in);
					}
				}
				RV1=pollardPReille(n,b);
				M1=RV1.duree;
				res1=RV1.res;
				afficheResultat(n,res1);
				tempsCalcul(M1);
			}
			else if(m.equals("p-1 uvsq")){
				System.out.println("-----METHODE P-1 DE POLLARD VERSION UNIVERSITE VERSAILLES SAINT QUENTIN-----");
				while(true){
					try{
						System.out.println("Veuillez saisir la borne (non comprise ex si vous mettez 5 alors B=2*3):");
						b=sc.nextInt();
						if (b<3){
							System.out.println("Votre borne est trop petite !\n");
						}
						else{
							break;
						}
					}
					catch (InputMismatchException E){
						System.out.println("Ceci n'est pas un nombre !\n");
						sc = new Scanner(System.in);
					}
					catch (ArithmeticException E){
						System.out.println("Ceci n'est pas un nombre valide !\n");
						sc = new Scanner(System.in);
					}
					catch (NumberFormatException E){
						System.out.println("Ceci n'est pas un nombre valide !\n");
						sc = new Scanner(System.in);
					}
				}
				RV1=pollardPuvsq(n,b);
				M1=RV1.duree;
				res1=RV1.res;
				afficheResultat(n,res1);
				tempsCalcul(M1);
			}
			else if(m.equals("p-1 sans borne")){
				while(true){
					try{
						System.out.println("Voulez vous utiliser le vrai calcul de m (si non, on prendra le factoriel) ? true/false");
						f=sc.nextBoolean();
						break;
					}
					catch (InputMismatchException E){
						System.out.println("Ceci n'est pas un boolean !\n");
						sc = new Scanner(System.in);
					}
				}
				if(f){
					System.out.println("-----METHODE P-1 DE POLLARD VERSION UNIVERSITE VERSAILLES SAINT QUENTIN SANS BORNE AVEC LE VRAI CALCUL DE m-----");
				}
				else{
					System.out.println("-----METHODE P-1 DE POLLARD VERSION UNIVERSITE VERSAILLES SAINT QUENTIN SANS BORNE AVEC e=B!-----");
				}
				RV1=pollardPuvsqSoluc(n,f);
				M1=RV1.duree;
				res1=RV1.res;
				afficheResultat(n,res1);
				tempsCalcul(M1);
			}
			else if(m.equals("p+1")){
				while(true){
					try{
						System.out.println("Veuillez saisir la borne (non comprise ex si vous mettez 5 alors B=2*3):");
						b=sc.nextInt();
						if (b<3){
							System.out.println("Votre borne est trop petite !\n");
						}
						else{
							break;
						}
					}
					catch (InputMismatchException E){
						System.out.println("Ceci n'est pas un nombre !\n");
						sc = new Scanner(System.in);
					}
					catch (ArithmeticException E){
						System.out.println("Ceci n'est pas un nombre valide !\n");
						sc = new Scanner(System.in);
					}
					catch (NumberFormatException E){
						System.out.println("Ceci n'est pas un nombre valide !\n");
						sc = new Scanner(System.in);
					}
				}
				
				sc = new Scanner(System.in);
				while(true){
					try{
						System.out.println("Voulez vous utiliser le vrai calcul de m (si non, on prendra le factoriel) ? true/false");
						f=sc.nextBoolean();
						break;
					}
					catch (InputMismatchException E){
						System.out.println("Ceci n'est pas un boolean !\n");
						sc = new Scanner(System.in);
					}
				}
				if(f){
					System.out.println("-----METHODE P+1 DE WILLIAMS AVEC LE VRAI CALCUL DE m-----");
				}
				else{
					System.out.println("-----METHODE P+1 DE WILLIAMS AVEC e=B!-----");
				}
				RV1=williamsMinus1(n,b,f);
				M1=RV1.duree;
				res1=RV1.res;
				afficheResultat(n,res1);
				tempsCalcul(M1);
			}
			else if(m.equals("p+1 sans borne")){
				while(true){
					try{
						System.out.println("Voulez vous utiliser le vrai calcul de m (si non, on prendra le factoriel) ? true/false");
						f=sc.nextBoolean();
						break;
					}
					catch (InputMismatchException E){
						System.out.println("Ceci n'est pas un boolean !\n");
						sc = new Scanner(System.in);
					}
				}
				if(f){
					System.out.println("-----METHODE P+1 DE WILLIAMS SANS BORNE AVEC LE VRAI CALCUL DE m-----");
				}
				else{
					System.out.println("-----METHODE P+1 DE WILLIAMS SANS BORNE AVEC e=B!-----");
				}
				RV1=williamsMinus1Soluc(n,f);
				M1=RV1.duree;
				res1=RV1.res;
				afficheResultat(n,res1);
				tempsCalcul(M1);
			}
			else if(m.equals("rho")){
				System.out.println("-----METHODE RHO DE POLLARD-----");
				RV1=rho(n);
				M1=RV1.duree;
				res1=RV1.res;
				afficheResultat(n,res1);
				tempsCalcul(M1);
			}
			else if(m.equals("crible quadratique")){
			}
			else if(m.equals("courbes elliptiques")){
			}
			else if(m.equals("retour")){
				break;
			}
			else{
				System.out.println("je n'ai pas compris votre message.");
			}
		}
	}
	
	//RHO DE POLLARD
	
	public static ReturningValues rho(BigInteger N){
		//https://introcs.cs.princeton.edu/java/99crypto/PollardRho.java.html
		long start,duree;
		start = System.currentTimeMillis();
		SecureRandom random = new SecureRandom();
		BigInteger divisor;
		BigInteger c  = new BigInteger(N.bitLength(), random);
        BigInteger x  = new BigInteger(N.bitLength(), random);
        BigInteger xx = x;

        // check divisibility by 2
        if (N.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0){
			duree=System.currentTimeMillis()-start;
			return new ReturningValues(BigInteger.valueOf(2),duree);
		}

        do {
			System.out.print("x="+x);
			System.out.println(" ,y="+xx+" et c ="+c);
            x  =  x.multiply(x).mod(N).add(c).mod(N);
			System.out.print("x=x²+c [n]="+x);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
			System.out.println(" et y=y²+c [n](2 fois)="+xx);
            divisor = x.subtract(xx).gcd(N);
			System.out.println("on trouve res=PGCD(x-y,n)="+divisor);
        } while((divisor.compareTo(BigInteger.ONE)) == 0);
		duree=System.currentTimeMillis()-start;
        return new ReturningValues(divisor,duree);
    }
	
	//P+1 WILLIAMS
	
	public static ReturningValues williamsMinus1(BigInteger n,int b,boolean m){
		long start,duree;
		
		start = System.currentTimeMillis();
		
		List<BigInteger> listB=premier(b);
		BigInteger P,G,res=BigInteger.ZERO;
		int milieu=listB.size()/2;
		
		P=nextRandomBigInteger(n);
		G=P.gcd(n);
		if(!((G).equals(BigInteger.ONE))){
			res=G;
		}
		else{
			res=williams(n,listB,milieu,P,m);
		}
		duree = System.currentTimeMillis() - start;
		
		if (res.equals(BigInteger.ZERO)){
			return new ReturningValues(res,-1);
		}
		return new ReturningValues(res,duree);
	}
	
	public static ReturningValues williamsMinus1Soluc(BigInteger n,boolean m){
		long start,duree;
		
		start = System.currentTimeMillis();
		
		BigInteger P,G,res=BigInteger.ZERO;
		
		P=nextRandomBigInteger(n);
		G=P.gcd(n);
		if(!((G).equals(BigInteger.ONE))){
			res=G;
		}
		else{
			res=williamsSoluc(n,P,m,BigInteger.ZERO);
		}
		duree = System.currentTimeMillis() - start;
		
		if (res.equals(BigInteger.ZERO)){
			return new ReturningValues(res,-1);
		}
		return new ReturningValues(res,duree);
	}
	
	public static BigInteger williams(BigInteger n, List<BigInteger> liste,int s, BigInteger P, boolean m){
		BigInteger e,x=BigInteger.valueOf(2),tmp=P,B;
		//https://github.com/defeo/MA2-AlgoC/blob/gh-pages/sources/factor.c
		
		if(s<0 || s>=liste.size()){
			return BigInteger.ZERO;
		}
		B=liste.get(s);
		System.out.print("Pour P="+P+" et B="+B);
		if(!m){
			e=facto(B);
		}
		else{
			e=calculerM(B);
		}
		if(n.bitLength()<250){
			System.out.println(" e="+e);
		}
		
		for(int i=e.bitLength()-1;i>=0;i--){
			if(e.testBit(i)){
				/*
				Si le bit vaut 1
				x <- x + tmp
				tmp <- 2*tmp
				*/
				// Formule d'addition : x_{2n-1} = x_{n-1} * x_n - x
				x=x.multiply(tmp);
				x=x.subtract(P);
				// Formule de dédoublement : x_{2n} = x_n^2 - 2
				tmp=tmp.multiply(tmp);
				tmp=tmp.subtract(BigInteger.valueOf(2));
			}
			else{
				/* 
				Si le bit vaut 0
				x <- 2*x
				tmp <- x + tmp
				*/
				//addition
				tmp=x.multiply(tmp);
				tmp=tmp.subtract(P);
				//dédoublement
				x=x.multiply(x);
				x=x.subtract(BigInteger.valueOf(2));
			}
			x=x.mod(n);
			tmp=tmp.mod(n);
		}
		x=x.subtract(BigInteger.valueOf(2));
		x=x.gcd(n);
		System.out.println("x="+x);
		if(x.equals(n)){
			System.out.println("ERREUR");
		}
		else if(x.equals(BigInteger.ONE)){
			System.out.println("x=1, la borne est trop petite, on doit recommencer !");
			System.out.println();
			return williams(n,liste,s+1,P,m);
		}
		System.out.println();
		return x;
	}
	
	public static BigInteger williamsSoluc(BigInteger n, BigInteger P, boolean m,BigInteger y){
		BigInteger e,x=BigInteger.valueOf(2),tmp=P,B;
		//https://github.com/defeo/MA2-AlgoC/blob/gh-pages/sources/factor.c
		
		B=nextPremier(y);
		System.out.print("Pour P="+P+" et B="+B);
		if(!m){
			e=facto(B);
		}
		else{
			e=calculerM(B);
		}
		if(n.bitLength()<250){
			System.out.println(" e="+e);
		}
		
		for(int i=e.bitLength()-1;i>=0;i--){
			if(e.testBit(i)){
				/*
				Si le bit vaut 1
				x <- x + tmp
				tmp <- 2*tmp
				*/
				// Formule d'addition : x_{2n-1} = x_{n-1} * x_n - x
				x=x.multiply(tmp);
				x=x.subtract(P);
				// Formule de dédoublement : x_{2n} = x_n^2 - 2
				tmp=tmp.multiply(tmp);
				tmp=tmp.subtract(BigInteger.valueOf(2));
			}
			else{
				/* 
				Si le bit vaut 0
				x <- 2*x
				tmp <- x + tmp
				*/
				//addition
				tmp=x.multiply(tmp);
				tmp=tmp.subtract(P);
				//dédoublement
				x=x.multiply(x);
				x=x.subtract(BigInteger.valueOf(2));
			}
			x=x.mod(n);
			tmp=tmp.mod(n);
		}
		x=x.subtract(BigInteger.valueOf(2));
		x=x.gcd(n);
			
		System.out.println("x="+x);
		
		if(x.equals(n)){
			System.out.println("ERREUR");
		}
		else if(x.equals(BigInteger.ONE)){
			System.out.println("x=1, la borne est trop petite, on doit recommencer !");
			System.out.println();
			return williamsSoluc(n,P,m,B.add(BigInteger.ONE));
		}
		System.out.println();
		return x;
	}
	
	//P-1 POLLARD
	
	public static ReturningValues pollardPReille(BigInteger n,int b){
		long start,duree;
		start = System.currentTimeMillis();
		List<BigInteger> listB=premier(b);
		List<BigInteger> A=listePremierAvec(n,b);
		BigInteger m,rmod,rgcd,res=BigInteger.ZERO;
		BigInteger[] M=new BigInteger[listB.size()];
		boolean reussite=false;
		System.out.println();
		int i;
		
		outer:
		for(BigInteger a : A){
			i=0;
			for (BigInteger B : listB){
				System.out.print("Pour a="+a+" et B="+B+" m=");
				if (a.equals(A.get(0))){
					m=calculerM(B); //on le calcule qu'une fois
					M[listB.indexOf(B)]=m;
				}
				else{
					m=M[listB.indexOf(B)];
				}
				System.out.println(m);
				i++;
				res=(a.modPow(m,n)).subtract(BigInteger.ONE);
				rmod=res.mod(n);
				rgcd=res.gcd(n);
				System.out.print("a^m -1 [n] ="+rmod);
				System.out.println("       PGCD(a^m -1,n)="+rgcd);
				//exponentiation modulaire perso
				if(!((rmod).equals(BigInteger.ZERO)) && !((rgcd).equals(BigInteger.ONE))){
					//gcd perso
					res= res.gcd(n);
					reussite=true;
					System.out.println();
					break outer;
				}
				else{
					System.out.println("a^m -1 [n]=0 ou PGCD(a^m -1,n)=1. On doit recommencer en changeant a et/ou b !");
					System.out.println();
				}
			}
		}
		duree = System.currentTimeMillis() - start;
		if (!reussite){
			return new ReturningValues(BigInteger.ZERO,-1);
		}
		return new ReturningValues(res,duree);
	}
	
	public static ReturningValues pollardPuvsq(BigInteger n,int b){
		long start,duree;
		
		start = System.currentTimeMillis();
		
		List<BigInteger> listB=premier(b);
		BigInteger a,G,res=BigInteger.ZERO;
		int milieu=listB.size()/2;
		
		a=nextRandomBigInteger(n);		
		G=a.gcd(n);
		if(!((G).equals(BigInteger.ONE))){
			res=G;
		}
		else{
			res=uvsq(n,listB,milieu,a);
		}
		duree = System.currentTimeMillis() - start;
		
		if (res.equals(BigInteger.ZERO)){
			return new ReturningValues(res,-1);
		}
		return new ReturningValues(res,duree);
	}
	
	public static ReturningValues pollardPuvsqSoluc(BigInteger n,boolean f){
		long start,duree;
		BigInteger a,G,res=BigInteger.ZERO;
		
		start = System.currentTimeMillis();
		
		a=nextRandomBigInteger(n);
		G=a.gcd(n);
		if(!((G).equals(BigInteger.ONE))){
			res=G;
		}
		else{
			res=uvsqSoluc(n,a,BigInteger.ZERO,f);
		}
		duree = System.currentTimeMillis() - start;
		return new ReturningValues(res,duree);
	}
	
	public static BigInteger uvsq(BigInteger n, List<BigInteger> liste,int s, BigInteger a){
		//http://defeo.lu/in420/dm-p-1
		BigInteger e,x,b,B;
		
		if(s<0 || s>=liste.size()){
			return BigInteger.ZERO;
		}
		B=liste.get(s);
		System.out.print("Pour a="+a+" et B="+B);
		e=facto(B);
		if(n.bitLength()<250){
			System.out.println(" e=B!="+e);
		}
		b=a.modPow(e,n);
		//exponentiation modulaire perso
		x=n.gcd(b.subtract(BigInteger.ONE));
		System.out.println("x=PGCD(a^e -1 [n],n)="+x);
		//gcd perso
		if(x.equals(n)){
			System.out.println("x=n, la borne est trop grande, on doit recommencer !");
			System.out.println();
			return uvsq(n,liste,s-1,a);
		}
		else if(x.equals(BigInteger.ONE)){
			System.out.println("x=1, la borne est trop petite, on doit recommencer !");
			System.out.println();
			return uvsq(n,liste,s+1,a);
		}
		System.out.println();
		return x;
	}
	
	//Optimiser nextpremier pour améliorer cette méthode et toutes les autres ou on supprime la borne
	public static BigInteger uvsqSoluc(BigInteger n, BigInteger a,BigInteger y,boolean f){
		BigInteger e,x,b,B;
		
		B=nextPremier(y); //MAIS PQ SA PREND AUTANT DE TEMPS ???
		System.out.print("Pour a="+a+" et B="+B);
		if(!f){
			e=facto(B);
		}
		else{
			e=calculerM(B);
		}
		if(n.bitLength()<250){
			System.out.print(" e="+e);
		}
		System.out.println();
		b=a.modPow(e,n);
		//exponentiation modulaire perso
		x=n.gcd(b.subtract(BigInteger.ONE));
		System.out.println("x=PGCD(a^e -1 [n],n)="+x);
		//gcd perso
		if(x.equals(BigInteger.ONE)){
			System.out.println("x=1, la borne est trop petite, on doit recommencer !");
			System.out.println();
			return uvsqSoluc(n,a,B.add(BigInteger.ONE),f);
		}
		System.out.println();
		return x;
	}
	
	//AUTRES METHODES
	
	public static BigInteger nextRandomBigInteger(BigInteger n) {
		Random rand = new Random();
		BigInteger result = new BigInteger(n.bitLength(), rand);
		while( result.compareTo(n) >= 0 || result.equals(BigInteger.ZERO)) {
			result = new BigInteger(n.bitLength(), rand);
		}
		return result;
	}
	
	//A OPTIMISER
	public static BigInteger calculerM(BigInteger b){
		List<BigInteger> listB=premier(b.intValue()+1);
		BigInteger res=BigInteger.valueOf(1);
		int puis=1;
		
		System.out.print(" ");
		for(BigInteger B : listB){
			while((B.pow(puis+1)).compareTo(b.add(BigInteger.ONE))==-1){ //+1 car b est exclu
				puis++;
			}
			res=res.multiply(B.pow(puis));
			puis=1;
		}
		return res;
	}
	
	public static BigInteger[] calculerMtab(List<BigInteger> listB){
		//était utilisé pour calculer tous les m avant mais comme on sait pas quand on s'arrete c'est pas ouf :/.
		BigInteger[] res=new BigInteger[listB.size()];
		BigInteger B;
	
		for(int i=0;i<listB.size();i++){
			B=listB.get(i);
			System.out.print("Pour B="+B+" m=");
			res[i]=calculerM(B);
			System.out.println("="+res[i]);
		}
		return res;
	}
	
	public static BigInteger[] chiffrerMessage(BigInteger[] tab,BigInteger e,BigInteger n){
		int t=tab.length;
		BigInteger[] res=new BigInteger[t];
		for (int i=0;i<t;i++){
			res[i]=tab[i].modPow(e,n);
			//exponentiation modulaire perso
		}
		return res;
	}
	
	public static void tempsCalcul(long duree){
		double s,h,min,ha,mina;
		
		if(duree>=0){
			s= (double)duree/(double)1000;
			min=s/60;
			mina=(double) Math.round(min * 1000) / 1000; 
			h=min/60;
			ha=(double) Math.round(h * 1000) / 1000; 
			System.out.println("Il a fallu "+duree+" millisecondes pour le factoriser soit "+s+" secondes soit "+mina+" minutes soit "+ha+" heures.");
		}
		System.out.println();
	}
	
	public static void afficheResultat(BigInteger n,BigInteger res){
		if (res.equals(BigInteger.ZERO)){
			System.out.println("Le programme n'a pas pu factoriser n, vous devez changer les paramètres si ils existent !");
		}
		else{
			System.out.println("la décomposition de "+n+" est :");
			System.out.println(res+" * "+n.divide(res));
		}
	}
	
	public static BigInteger facto(BigInteger n){
		BigInteger result = BigInteger.ONE;

		while (!n.equals(BigInteger.ZERO)) {
			result = result.multiply(n);
			n = n.subtract(BigInteger.ONE);
		}

		return result;
	}
	
	public static BigInteger inverseE(BigInteger e, BigInteger phi){
		BigInteger d=euclideEtendu(e,phi)[1];
		if (d.compareTo(BigInteger.ZERO)==-1){
			d=d.add(phi);
		}
		return d;
	}
	
    public static BigInteger[] euclideEtendu(BigInteger a, BigInteger b) {
		BigInteger x = a, y=b;
		BigInteger[] qrem = new BigInteger[2];
		BigInteger[] result = new BigInteger[3];
		BigInteger x0 = BigInteger.ONE, x1 = BigInteger.ZERO;
		BigInteger y0 = BigInteger.ZERO, y1 = BigInteger.ONE;
		while (true){
			qrem = x.divideAndRemainder(y); x = qrem[1];
			x0 = x0.subtract(y0.multiply(qrem[0]));
			x1 = x1.subtract(y1.multiply(qrem[0]));
			if (x.equals(BigInteger.ZERO)) {result[0]=y; result[1]=y0; result[2]=y1; return result;};
			qrem = y.divideAndRemainder(x); y = qrem[1];
			y0 = y0.subtract(x0.multiply(qrem[0]));
			y1 = y1.subtract(x1.multiply(qrem[0]));
			if (y.equals(BigInteger.ZERO)) {result[0]=x; result[1]=x0; result[2]=x1; return result;};
		}
	//result[0]=PGCD,result[1]=inverse mod de a et result[2]=inverse mod de b
    }

	public static BigInteger sqrt(BigInteger x) {
		BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
		BigInteger div2 = div;
		// Loop until we hit the same value twice in a row, or wind
		// up alternating.
		for(;;) {
			BigInteger y = div.add(x.divide(div)).shiftRight(1);
			if (y.equals(div) || y.equals(div2))
				return y;
			div2 = div;
			div = y;
		}
	}
	
	//TROUVER UN/DES NOMBRE(S) PREMIER(S)
	
	//A OPTIMISER je comprends pas pk sa prend 1,5 sec à chaque fois
	public static BigInteger nextPremier(BigInteger y){
		AKS aks=new AKS();
		
		if(y.compareTo(BigInteger.ONE)==-1){
			y=BigInteger.ONE;
		}
		while(!aks.checkIsPrime(y)){
			y=y.add(BigInteger.ONE);
		}
		return y;
	}
	
	public static String premierAvec(BigInteger phi,int nombre){
		String str="";
		int j=0;
		BigInteger i=BigInteger.valueOf(2);
		while (j<nombre && i.compareTo(phi)==-1){
			if((i.gcd(phi)).equals(BigInteger.ONE)){
				//GCD perso
				str=str+i+" ";
				j++;
			}
			i=i.add(BigInteger.ONE);
		}
		str=str+"\n";
		return str;
	}
	
	public static List<BigInteger> listePremierAvec(BigInteger phi,int nombre){
		List<BigInteger> res=new ArrayList<BigInteger>();
		int j=0;
		BigInteger i=BigInteger.valueOf(2);
		while (j<nombre && i.compareTo(phi)==-1){
			if((i.gcd(phi)).equals(BigInteger.ONE)){
				//GCD perso
				res.add(i);
				j++;
			}
			i=i.add(BigInteger.ONE);
		}
		return res;
	}
	
	public static String premier(BigInteger i,int nombre){
		AKS aks =new AKS();
		String str="";
		int j=0;
		while (j<nombre){
			if(aks.checkIsPrime(i)){
				str=str+i+" ";
				j++;
			}
			i=i.add(BigInteger.ONE);
		}
		str=str+"\n";
		return str;
	}
	
	public static List<BigInteger> premier(int b){
		AKS aks =new AKS();
		List<BigInteger> res=new ArrayList<BigInteger>();
		BigInteger i=BigInteger.valueOf(2);
		while (i.compareTo(BigInteger.valueOf(b))==-1){
			if(aks.checkIsPrime(i)){
				res.add(i);
			}
			i=i.add(BigInteger.ONE);
		}
		return res;
	}
	
	//A OPTIMISER pour de gros bits genre 1024 crible eratostene plus gauss pour savoir cb de nombres
	public static void premier(int deb,int nombre){
		BigInteger i;
		if(deb<=0){
			i=BigInteger.valueOf(1);
		}
		else{
			i=BigInteger.valueOf(deb);
		}
		AKS aks =new AKS();
		int j=0;
		while (j<nombre){
			if(aks.checkIsPrime(i)){
				System.out.print(i+" ");
				j++;
			}
			i=i.add(BigInteger.ONE);
		}
		System.out.println();
	}

	//CONVERSION
	
	public static String convertString(BigInteger[] tab){
		int n=tab.length;
		String str="";
		for(int i=0;i<n;i++){
			str=str+tab[i]+" ";
		}
		return str;
	}
	
	public static BigInteger[] convertStringBigInteger(String m){
		char[] mess=m.toCharArray();
		int n=mess.length;
		BigInteger[] res=new BigInteger[n];
		int a=0;
		
		for(int i=0;i<mess.length;i++){
			res[i]=BigInteger.valueOf((int) mess[i]);
		}
		return res;
	}
	
	public static BigInteger[] convertChiffrerBigInt(String m){
		String nombres[]=m.split(" ");
		int n=nombres.length;
		BigInteger res[]=new BigInteger[n];
		for (int i=0;i<n;i++){
			res[i]=new BigInteger(nombres[i]);
		}
		return res;
	}
	
	public static String convertBigIntegerString(BigInteger[] m){
		String res="";
		int n=m.length;
		
		for(int i=0;i<m.length;i++){
			res=res+ (char) (m[i].intValue());
		}
		return res;
	}	
}