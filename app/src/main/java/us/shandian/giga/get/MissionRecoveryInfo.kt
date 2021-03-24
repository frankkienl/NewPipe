package us.shandian.giga.get

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.schabi.newpipe.extractor.MediaFormat
import org.schabi.newpipe.extractor.stream.AudioStream
import org.schabi.newpipe.extractor.stream.Stream
import org.schabi.newpipe.extractor.stream.SubtitlesStream
import org.schabi.newpipe.extractor.stream.VideoStream
import java.io.Serializable

@Parcelize
class MissionRecoveryInfo(
    var format: MediaFormat,
    var desired: String? = null,
    var isDesired2: Boolean = false,
    var desiredBitrate: Int = 0,
    var kind: Char = Char.MIN_VALUE,
    var validateCondition: String? = null
) : Serializable, Parcelable {
    constructor(stream: Stream) : this(format = stream.getFormat()!!) {
        when (stream) {
            is AudioStream -> {
                desiredBitrate = stream.average_bitrate
                isDesired2 = false
                kind = 'a'
            }
            is VideoStream -> {
                desired = stream.getResolution()
                isDesired2 = stream.isVideoOnly()
                kind = 'v'
            }
            is SubtitlesStream -> {
                desired = stream.languageTag
                isDesired2 = stream.isAutoGenerated
                kind = 's'
            }
            else -> throw RuntimeException("Unknown stream kind")
        }
    }

    override fun toString(): String {
        val info: String
        val str = StringBuilder()
        str.append("{type=")
        when (kind) {
            'a' -> {
                str.append("audio")
                info = "bitrate=$desiredBitrate"
            }
            'v' -> {
                str.append("video")
                info = "quality=$desired videoOnly=$isDesired2"
            }
            's' -> {
                str.append("subtitles")
                info = "language=$desired autoGenerated=$isDesired2"
            }
            else -> {
                info = ""
                str.append("other")
            }
        }
        str.append(" format=")
            .append(format.getName())
            .append(' ')
            .append(info)
            .append('}')
        return str.toString()
    }
}
