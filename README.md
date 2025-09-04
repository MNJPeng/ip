# ☠️ **Mael** 

> Ready to receive missions

## Purpose
- Load and save missions
- Track completion
- Easy Lookup

## *Mission Variants*
- [x] todo
- [x] deadline
- [x] event
- [ ] recurring event (coming soon)

## Installation Process
1. Download [here](https://github.com/MNJPeng/ip/releases/tag/v0.1)
2. Run java -jar Mael.jar
3. Complete

## Extra modifications
To speed up or disable startup and shutdown sequences, `main` can be modified.

```
public static void main(String[] args) throws InterruptedException {
  new Mael("data/Mael.txt").run(true, true);
}
```
- To speed up:
  - Set the first true to false
- To disable:
  - Set the second true to false
