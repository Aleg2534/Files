package buildings;

public class Dwelling {
    private DwellingFloor[] dwellingFloors;

    public Dwelling(int numberOfFloors, int[] numbersOfFlats) {
        dwellingFloors= new DwellingFloor[numberOfFloors];
        for(int i = 0; i<numberOfFloors;i++)
        {
            dwellingFloors[i]=new DwellingFloor(numbersOfFlats[i]);
        }
    }

    public Dwelling(DwellingFloor[] dwellingFloors) {
        this.dwellingFloors = dwellingFloors;
    }

    public int getNumbersOfFlats()
    {
        int i=0;
        for(DwellingFloor dwellingFloor: dwellingFloors)
        {
            i+=dwellingFloor.getNumberFlats();
        }
        return i;
    }

    public int getNumbersOfRooms()
    {
        int i=0;
        for(DwellingFloor dwellingFloor: dwellingFloors)
        {
            i+=dwellingFloor.getNumberRoomsOnFloor();
        }
        return i;
    }

    public double getDwellingSquare()
    {
        double i=0;
        for(DwellingFloor dwellingFloor: dwellingFloors)
        {
            i+=dwellingFloor.getFloorSquare();
        }
        return i;
    }

    public Flat getBestSpace()
    {
        Flat bestSpase=dwellingFloors[0].getBestSpace();
        for(DwellingFloor dwellingFloor: dwellingFloors)
        {
            if(bestSpase.getSquare()<dwellingFloor.getBestSpace().getSquare())
            {
                bestSpase=dwellingFloor.getBestSpace();
            }
        }
        return bestSpase;
    }

    public DwellingFloor[] getDwellingFloors() {
        return dwellingFloors;
    }

    public DwellingFloor getDwellingFloor(int numberOfFloor)
    {
        return dwellingFloors[numberOfFloor];
    }

    public void changeFloor(int numberOfFloor, DwellingFloor floor)
    {
        dwellingFloors[numberOfFloor]=floor;
    }

    public Flat getFlatByNumber(int numberOfFlat)
    {
        for(DwellingFloor dwellingFloor : dwellingFloors)
        {
            if(numberOfFlat>dwellingFloor.getNumberFlats())
            {
                numberOfFlat-=dwellingFloor.getNumberFlats();
            }
            else
            {
                return dwellingFloor.getFlatByNumber(numberOfFlat);
            }
        }
        return null;
    }
}