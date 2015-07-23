func printAreaSwitchStatement() {
        var area = calculateArea()
        if area == Double.infinity {
            println("something went wrong!")
            return
        }
        switch area {
            case 0:
                println ("Area is zero")
            case 1:
                println ("Area is one sq units")
            default:
                println ("Area is \(area) sq units") }
}

class SomeClass {
 let x = 2 }

class Hello
<T>: SomeProtocol {
}

class Hello
<T>: SomeProtocol { }

struct SomeStruct {
 let x = 2 }

struct SomeStruct
<T> { }

struct Hello <T> { }

struct Hello
<T> {
}

protocol SomeProtocol {

}

protocol SomeOtherProtocol: X {

}

protocol
SomeProtocol {

}

protocol SomeProtocol { }

protocol
SomeOtherProtocol: X { }

protocol SomeOtherProtocol
: X { }

public extension SomeExtension {

}

extension SomeExtension: X { }

extension
SomeExtension {}

extension SomeExtension: X { }

extension
SomeExtension: X { }

extension
SomeExtension: X {

}

func demo() {}

func max<T, U: Comparable>(f: T -> U ) -> U? {
    return nil
}

func max<T, U: Comparable>(f: T -> U ) -> U? {
    return nil }

protocol SomeProtocol {}

extension SomeExtension: X { }

class SomeClass {/* comment */}

class SomeClass {}

class SomeClass {/* comment
*/ }

class SomeClass {/* comment space comment
*/ /* comment
  comment */ }

class SomeClass {
/* */}

class SomeClass {
    var x: Int {
        set {
            self.x = 10 }

        get {
            return 10 }
    }
}

struct SomeStruct {
    var x: Int {
        set {
            x = 10
        }

        get {
            return 10
        }
    }
}

struct SomeStruct {
    var x: Int {
        get {
            return 10 }
    }
}

@objc
class ExampleClass {
    var enabled: Bool {
        @objc(isEnabled)
        get {
            return true }
        set {
            self.enabled = true
        }
    }
}

@objc
class SomeClass {
    var enabled: Bool {
        @objc(isEnabled)
        get {
            return true
        }
        set {
            self.enabled = true
        }
    }
}

struct Rect {
    var center: Point {
        get {
            let centerX = origin.x + (size.width / 2)
            var centerY = origin.y + (size.height / 2)
            return Point(x: centerX, y: centerY)
        }
        set(newCenter) {
            var origin.x = newCenter.x - (size.width / 2)
            var origin.y = newCenter.y - (size.height / 2) }
    }
}


init(length: Double, breadth: Double) {
    // store the row and column of the square in the grid
    self.length = length
    self.breadth = breadth }

init(length: Double, breadth: Double) {
    // store the row and column of the square in the grid
    self.length = length
    self.breadth = breadth
}

func printAreaIfStatement() {
        var area = calculateArea()
        if area == Double.infinity {
            println("something went wrong!")
            return
        }

        if area == 0 {
            println ("Area is zero") }

        else if area == 1 {
            println ("Area is 1") }

        else if area == 2 {
            println ("Area is 2")
        }

        else { println ("Area is \(area)") }
}

    func funWithLoops() {
        let lengthArray = [0,1,2,3,4]
        let breadthArray = [0,1,2,3,4]

        for var x = 0; x < lengthArray.count; x+=1 {
            println(x) }

        for breadth in breadthArray {
            println(breadth) }

        while false {
            println("Will never be executed") }

        repeat { /* infinite loop */ } while true

        repeat { /* infinite
         loop */ } while true
    }
